package com.superzmz.www.interceptors;

import com.superzmz.www.bean.Privilege;
import com.superzmz.www.bean.Role;
import com.superzmz.www.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class PrivilegeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        String privUrl = httpServletRequest.getRequestURI();
        //如果未登录
        if (user == null){
            if (privUrl.startsWith("/user_login") ){
                return true;
            }else{
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/user_loginUI.action");
            }
        }else {//已登录
            Collection<String> allPrivilegeUrls = (Collection<String>) httpServletRequest.getSession().getServletContext()
                    .getAttribute("allPrivilegeUrls");
            boolean hasPriv = hasPrivilegeByUrl(privUrl, user, allPrivilegeUrls);
            if (hasPriv){
                return true;
            }else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/noPrivilegeError.jsp");
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
    public boolean hasPrivilegeByUrl(String privUrl,User user,Collection<String> allPrivilegeUrls){
        // 超级管理有所有的权限
        if ("admin".equals(user.getLoginName())) {
            return true;
        }

        // >> 去掉后面的参数
        int pos = privUrl.indexOf("?");
        if (pos > -1) {
            privUrl = privUrl.substring(0, pos);
        }
        // >>去掉 .action后缀
        if (privUrl.endsWith(".action")) {
            privUrl = privUrl.substring(0, privUrl.length() - 7);
        }

        // >> 去掉UI后缀
        if (privUrl.endsWith("UI")) {
            privUrl = privUrl.substring(0, privUrl.length() - 2);
        }

        // 如果本URL不需要控制，则登录用户就可以使用

        if (!allPrivilegeUrls.contains(privUrl)) {
            return true;
        } else {
            // 普通用户要判断是否含有这个权限
            for (Role role : user.getRoles()) {
                for (Privilege priv : role.getPrivileges()) {
                    if (privUrl.equals(priv.getUrl())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
