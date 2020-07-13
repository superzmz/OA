package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.LeaveApprover;
import com.superzmz.www.service.LeaveApproverService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LeaveApproverServiceImpl extends BaseDaoImpl<LeaveApprover> implements LeaveApproverService {

    @Resource
    private SessionFactory sessionFactory;
    @Override
    public Class getEntityClass() {
        return LeaveApprover.class;
    }

    @Override
    public List<LeaveApprover> getByLeaveId(Long id) {
        String sql = "FROM LeaveApprover lea WHERE lea.leaveId = ?";
        return getCurrentSession().createQuery(sql)
                .setParameter(0,id)
                .list();
    }


    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }


}
