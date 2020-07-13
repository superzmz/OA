package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.Role;
import com.superzmz.www.service.RoleService;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl extends BaseDaoImpl<Role> implements RoleService{

    @Override
    public Class getEntityClass() {
        return Role.class;
    }

}
