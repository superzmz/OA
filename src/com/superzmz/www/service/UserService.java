package com.superzmz.www.service;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.bean.Privilege;
import com.superzmz.www.bean.User;

import java.util.List;

public interface UserService extends BaseDao<User>{

    User findByNameAndPassword(String loginName, String password);

    List<Privilege> preparePrivileges(User user, List<Privilege> topList);

    void changeWorkStatus(User user);

    boolean hasPrivilegeByName(String name, User user);
}
