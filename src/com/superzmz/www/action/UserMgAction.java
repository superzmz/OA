package com.superzmz.www.action;

import com.superzmz.www.Constants;
import com.superzmz.www.bean.*;
import com.superzmz.www.service.*;
import com.superzmz.www.tool.DepartmentUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@Scope("prototype")
public class UserMgAction {

    @Resource
    private UserMgService userMgService;

    @Resource
    private TimecardService timecardService;

    @Resource
    private RoleService roleService ;
    @Resource
    private DepartmentService departmentService ;

    /**
     * action (addUI, editUI ׼�������ݲ�ͬ) �� ����ͬһ��ҳ�� saveUI.jsp
     * editUI��delete���� list.jspҳ���������ֻ��Ҫ���� model��id���ɣ�editUI���ڻ������ݣ�delete����ɾ������
     * SpringMVC�������ݹ����У���nameҪ�� list(String name,User model������name )��Ӧ������ʵ�����model���ƿ�Ϊ���������
     */


    @RequestMapping("/user_list.action")
    public String list(Integer currentPage, Map map, HttpSession session){
        // ��ҳ��ʾ
        if (currentPage == null ) {
            currentPage = Constants.PAGE_CURRENT_DEFAULT;
        }

        //����ѯ�Լ�
        User user = (User) session.getAttribute("user");
        List<Object> parameters = new ArrayList();
        parameters.add(user);
        //ASC DESC
        String sql = "FROM User u WHERE u <> ? ORDER BY u.createTime DESC";
        PageBean pageBean = userMgService.getPageBean(currentPage, Constants.PAGE_SIZE, sql, parameters);
        map.put("pageBean", pageBean);
        return "user/list";
    }

    @RequestMapping("/salary_list.action")
    public String salarylist(Integer currentPage, Map map, HttpSession session){
        // ��ҳ��ʾ
        if (currentPage == null ) {
            currentPage = Constants.PAGE_CURRENT_DEFAULT;
        }

        //����ѯ�Լ�
        User user = (User) session.getAttribute("user");
        List<Object> parameters = new ArrayList();
        parameters.add(user);
        //ASC DESC
        String sql = "FROM User u WHERE u <> ? ORDER BY u.createTime DESC";
        PageBean pageBean = userMgService.getPageBean(currentPage, Constants.PAGE_SIZE, sql, parameters);
        map.put("pageBean", pageBean);
        return "salary/list";
    }

    @RequestMapping("/salary_settleUI.action")
    public String settleUI(Long userId,Map map) throws Exception {
        User user = userMgService.getById(userId);
        String name = user.getName();
        map.put("name",name);
        String salary = user.getSalary();
        map.put("salary",salary);
        List<Timecard> personTimecard = timecardService.findPersonTimecard(userId);
        int count = personTimecard.size();
        map.put("count",count);
        int tardy = getTardy(userId);
        map.put("tardy",tardy);
        int daySalary = Integer.parseInt(salary);
        int result = count * daySalary - tardy * 50;
        map.put("result",result);
        return "salary/settleUI";
    }

    public int getTardy(Long userId){
        List<Timecard> personTimecard = timecardService.findPersonTimecard(userId);
        int tardy = 0;
        for (Timecard timecard:personTimecard){
            int type = timecard.getType();

            if (type == 2){
                tardy+=1;
            }
        }
        return tardy;
    }

    @RequestMapping("/user_delete.action")
    public String delete(Long userId) {
        userMgService.delete(userId);
        return "redirect:/user_list.action";
    }

    @RequestMapping("/user_addUI.action")
    public String addUI(Map map) {
        // ����û������� ׼�� departmentList���� �� roleList ���� ���ԣ�����request��
        List<Department> topList = departmentService.findTopList();
        List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
        map.put("departmentList", departmentList);

        // ׼������, roleList
        List<Role> roleList = roleService.findAll();
        map.put("roleList", roleList);
        return "user/saveUI";
    }

    @RequestMapping("/user_add.action")
    public String add(User model,Long departmentId,Long [] roleIds){

        // ��װ�������У���model��ʵ������ʱ��Ҳ����ʹ��model����Ҫ����δ��װ�����ԣ�
        // >> ������������
        model.setDepartment(departmentService.getById(departmentId));
        // >> ���ù����ĸ�λ
        List<Role> roleList = roleService.getByIds(roleIds);//���roleIds��null��service���ص��ǿռ��ϣ��ʰ�ȫ�Ըߣ������׿�ָ���쳣
        model.setRoles(new HashSet<Role>(roleList));
        // >> ����Ĭ������Ϊ1234��Ҫʹ��MD5ժҪ��
        String md5Digest = DigestUtils.md5Hex("1234");
        model.setPassword(md5Digest);
        model.setCreateTime(new Date());

        // ���浽���ݿ�
        userMgService.save(model);

        return "redirect:/user_list.action";
    }

    /** �޸�ҳ�� */
    @RequestMapping("/user_editUI.action")
    public String editUI(Long userId,Map map) throws Exception {
        // ׼������, departmentList
        List<Department> topList = departmentService.findTopList();
        List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
        map.put("departmentList", departmentList);

        // ׼������, roleList
        List<Role> roleList = roleService.findAll();
        map.put("roleList", roleList);

        // ׼�����Ե�����
        User user = userMgService.getById(userId);
        map.put("user", user);

        if (user.getDepartment() != null) {
            Long departmentId = user.getDepartment().getId();
            map.put("departmentId", departmentId);
        }
        if (user.getRoles() != null) {
            Long [] roleIds = new Long[user.getRoles().size()];
            int index = 0;
            for (Role role : user.getRoles()) {
                roleIds[index++] = role.getId();
            }
            map.put("roleIds", roleIds);
        }

        return "user/saveUI";
    }





    @RequestMapping("/user_edit.action")
    public String edit(User model, Long departmentId, Long [] roleIds, RedirectAttributes attrs) {
        // 1�������ݿ���ȡ��ԭ����
        User user = userMgService.getById(model.getId());

        // 2������Ҫ�޸ĵ�����
        user.setLoginName(model.getLoginName());
        user.setName(model.getName());
        user.setGender(model.getGender());
        user.setPhoneNumber(model.getPhoneNumber());
        user.setEmail(model.getEmail());
        user.setDescription(model.getDescription());
        // >> ������������
        user.setDepartment(departmentService.getById(departmentId));
        // >> ���ù����ĸ�λ
        List<Role> roleList = roleService.getByIds(roleIds);
        user.setRoles(new HashSet<Role>(roleList));

        // 3�����µ����ݿ�
        userMgService.update(user);

        return "redirect:/user_list.action";
    }


    /** ��ʼ������Ϊ1234 */
    @RequestMapping("/user_initPassword.action")
    public String initPassword(Long userId) throws Exception {
        // 1�������ݿ���ȡ��ԭ����
        User user = userMgService.getById(userId);

        // 2������Ҫ�޸ĵ����ԣ�Ҫʹ��MD5ժҪ��
        String md5Digest = DigestUtils.md5Hex("1234");
        user.setPassword(md5Digest);

        // 3�����µ����ݿ�
        userMgService.update(user);
        return "redirect:/user_list.action"; //�ض���action
    }
    /**н���޸�ҳ��**/
    @RequestMapping("/salary_settingUI.action")
    public String settingUI(Long userId,Map map) throws Exception{
        // ׼�����Ե�����
        User user = userMgService.getById(userId);
        map.put("user", user);
        return "salary/settingUI";
    }

    /**н���޸�**/
    @RequestMapping("/salary_setting.action")
    public String salaryedit(User model,HttpSession session,HttpServletRequest request) throws Exception{
        // 1�������ݿ���ȡ��ԭ����
        User user = userMgService.getById(model.getId());

        user.setSalary(request.getParameter("salary"));
        userMgService.update(user);
        return "redirect:/salary_list.action"; //�ض���action
    }
}
