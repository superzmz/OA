package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.User;
import com.superzmz.www.service.UserMgService;
import org.springframework.stereotype.Service;

@Service
public class UserMgServiceImpl extends BaseDaoImpl<User> implements UserMgService {

    @Override
    public Class getEntityClass() {
        return User.class;
    }
}
