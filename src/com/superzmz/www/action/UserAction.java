package com.superzmz.www.action;

import com.superzmz.www.bean.Privilege;
import com.superzmz.www.bean.User;
import com.superzmz.www.service.UserService;
import com.superzmz.www.tool.TextUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class UserAction {

    @Resource
    protected UserService userService;


    //��¼ҳ��
    @RequestMapping("/user_loginUI.action")
    public String loginUI(HttpServletResponse response){
        return "user/loginUI";
    }

    //��¼
    @RequestMapping("/user_login.action")
    public String login(User user, HttpSession session,Map map ) {
        // ��ʹǰ̨����������User����Ҳ����Ϊ��
        if (TextUtils.isEmpty(user.getLoginName()) || TextUtils.isEmpty(user.getPassword())) {
            return "user/loginUI";
        }
        if (session.getAttribute("user")!=null) {
            //�Ѿ���½��
            return "redirect:/home_index.action"; //��̨������
        }

        User u  = userService.findByNameAndPassword(user.getLoginName(),user.getPassword());
        if (u == null) {
            return "user/loginUI";
        } else {
            session.setAttribute("user", u);

            //׼��Ȩ������
            List<Privilege> topPrivilegeList = (List<Privilege>) session.getServletContext().getAttribute("topPrivilegeList");

            List<Privilege> copyPrivileges = userService.preparePrivileges(u,topPrivilegeList);
            session.setAttribute("privileges", copyPrivileges );

            System.out.println("privileges:"+copyPrivileges );

            Integer online = (Integer) session.getServletContext().getAttribute("online");
            if (online == null) {
                session.getServletContext().setAttribute("online", 1);
            }else{
                online++;
                session.getServletContext().setAttribute("online",online);
            }
            return "redirect:/home_index.action"; //��̨������
        }
    }

    /**
     * ע��
     */
    @RequestMapping("/user_logout.action")
    public String logout(HttpSession session) {
        // http://stackoverflow.com/questions/18209233/spring-mvc-how-to-remove-session-attribute
        session.invalidate();

        Integer online = (Integer) session.getServletContext().getAttribute("online");
        if (null !=online) {
            online--;
            session.getServletContext().setAttribute("online",online);
        }

        return "user/loginUI";
    }


    @RequestMapping("user_settingUI.action")
    public String settingUI() {
        return "user/settingUI";
    }


    @RequestMapping("user_setting.action")
    public String setting(User model,HttpSession session) {
        //��Ϊ���õ��ǵ�ǰ�û���ֱ�Ӵ�session��ȡ������
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.setName(model.getName());
            user.setGender(model.getGender());
            user.setPhoneNumber(model.getPhoneNumber());
            user.setEmail(model.getEmail());
            user.setDescription(model.getDescription());
            userService.update(user);
        }
        return "user/optSuccess";
    }

    @RequestMapping("user_settingPasswordUI.action")
    public String settingPasswordUI() {return "user/settingPasswordUI";}

    @RequestMapping("user_settingPassword.action")
    public String settingPassword(User model,HttpSession session,HttpServletRequest request){
        //��Ϊ���õ��ǵ�ǰ�û���ֱ�Ӵ�session��ȡ������
        User user = (User) session.getAttribute("user");
        if (user != null){
           String password = (String) model.getPassword();
           String password1 = (String) request.getParameter("password1");
           if(password != null && password.equals(password1)){
               String md5Digest = DigestUtils.md5Hex(password);
               user.setPassword(md5Digest);
               userService.update(user);
            }else {
               return "error";
           }
        }
        return "user/optSuccess";
    }
}
