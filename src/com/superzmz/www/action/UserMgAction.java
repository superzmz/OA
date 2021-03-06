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
     * action (addUI, editUI 准备的数据不同) 但 共用同一个页面 saveUI.jsp
     * editUI和delete均由 list.jsp页面操作，故只需要参数 model的id即可，editUI用于回显数据，delete用于删除数据
     * SpringMVC参数传递过程中，表单name要和 list(String name,User model的属性name )对应，其中实体变量model名称可为任意变量名
     */


    @RequestMapping("/user_list.action")
    public String list(Integer currentPage, Map map, HttpSession session){
        // 分页显示
        if (currentPage == null ) {
            currentPage = Constants.PAGE_CURRENT_DEFAULT;
        }

        //不查询自己
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
        // 分页显示
        if (currentPage == null ) {
            currentPage = Constants.PAGE_CURRENT_DEFAULT;
        }

        //不查询自己
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
        // 添加用户，首先 准备 departmentList数据 和 roleList 数据 回显，放入request中
        List<Department> topList = departmentService.findTopList();
        List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
        map.put("departmentList", departmentList);

        // 准备数据, roleList
        List<Role> roleList = roleService.findAll();
        map.put("roleList", roleList);
        return "user/saveUI";
    }

    @RequestMapping("/user_add.action")
    public String add(User model,Long departmentId,Long [] roleIds){

        // 封装到对象中（当model是实体类型时，也可以使用model，但要设置未封装的属性）
        // >> 设置所属部门
        model.setDepartment(departmentService.getById(departmentId));
        // >> 设置关联的岗位
        List<Role> roleList = roleService.getByIds(roleIds);//如果roleIds是null，service返回的是空集合，故安全性高，不会抛空指针异常
        model.setRoles(new HashSet<Role>(roleList));
        // >> 设置默认密码为1234（要使用MD5摘要）
        String md5Digest = DigestUtils.md5Hex("1234");
        model.setPassword(md5Digest);
        model.setCreateTime(new Date());

        // 保存到数据库
        userMgService.save(model);

        return "redirect:/user_list.action";
    }

    /** 修改页面 */
    @RequestMapping("/user_editUI.action")
    public String editUI(Long userId,Map map) throws Exception {
        // 准备数据, departmentList
        List<Department> topList = departmentService.findTopList();
        List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
        map.put("departmentList", departmentList);

        // 准备数据, roleList
        List<Role> roleList = roleService.findAll();
        map.put("roleList", roleList);

        // 准备回显的数据
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
        // 1，从数据库中取出原对象
        User user = userMgService.getById(model.getId());

        // 2，设置要修改的属性
        user.setLoginName(model.getLoginName());
        user.setName(model.getName());
        user.setGender(model.getGender());
        user.setPhoneNumber(model.getPhoneNumber());
        user.setEmail(model.getEmail());
        user.setDescription(model.getDescription());
        // >> 设置所属部门
        user.setDepartment(departmentService.getById(departmentId));
        // >> 设置关联的岗位
        List<Role> roleList = roleService.getByIds(roleIds);
        user.setRoles(new HashSet<Role>(roleList));

        // 3，更新到数据库
        userMgService.update(user);

        return "redirect:/user_list.action";
    }


    /** 初始化密码为1234 */
    @RequestMapping("/user_initPassword.action")
    public String initPassword(Long userId) throws Exception {
        // 1，从数据库中取出原对象
        User user = userMgService.getById(userId);

        // 2，设置要修改的属性（要使用MD5摘要）
        String md5Digest = DigestUtils.md5Hex("1234");
        user.setPassword(md5Digest);

        // 3，更新到数据库
        userMgService.update(user);
        return "redirect:/user_list.action"; //重定向到action
    }
    /**薪资修改页面**/
    @RequestMapping("/salary_settingUI.action")
    public String settingUI(Long userId,Map map) throws Exception{
        // 准备回显的数据
        User user = userMgService.getById(userId);
        map.put("user", user);
        return "salary/settingUI";
    }

    /**薪资修改**/
    @RequestMapping("/salary_setting.action")
    public String salaryedit(User model,HttpSession session,HttpServletRequest request) throws Exception{
        // 1，从数据库中取出原对象
        User user = userMgService.getById(model.getId());

        user.setSalary(request.getParameter("salary"));
        userMgService.update(user);
        return "redirect:/salary_list.action"; //重定向到action
    }
}
