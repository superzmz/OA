package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.Department;
import com.superzmz.www.service.DepartmentService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl extends BaseDaoImpl<Department> implements DepartmentService {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Class getEntityClass() {
        return Department.class;
    }

    @Override
    public List<Department> findTopList() {
        return sessionFactory.getCurrentSession().createQuery(//
                "FROM Department d WHERE d.parent IS NULL")//
                .list();
    }

    public List<Department> findChildren(Long parentId) {
        return sessionFactory.getCurrentSession().createQuery(//
                "FROM Department d WHERE d.parent.id=?")//
                .setParameter(0, parentId)//
                .list();
    }


}
