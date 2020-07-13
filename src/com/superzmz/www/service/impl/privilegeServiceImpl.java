package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.Privilege;
import com.superzmz.www.service.PrivilegeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class privilegeServiceImpl extends BaseDaoImpl<Privilege> implements PrivilegeService {

    @Override
    public Class getEntityClass() {
        return Privilege.class;
    }
    public List<Privilege> findTopList() {
        return getSession().createQuery(//
                "FROM Privilege p WHERE p.parent IS NULL")//
                .list();
    }

    public List<String> getAllPrivilegeUrls() {
        return getSession().createQuery(//
                "SELECT DISTINCT p.url FROM Privilege p WHERE p.url IS NOT NULL")//
                .list();
    }
}
