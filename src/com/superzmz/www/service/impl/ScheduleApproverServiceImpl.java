package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;

import com.superzmz.www.bean.Schedule;
import com.superzmz.www.bean.ScheduleApprover;
import com.superzmz.www.service.ScheduleApproverService;
import com.superzmz.www.service.ScheduleService;
import com.superzmz.www.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScheduleApproverServiceImpl extends BaseDaoImpl<ScheduleApprover> implements ScheduleApproverService {
    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private UserService userService;

    @Resource
    private ScheduleService scheduleService;

    @Override
    public Class getEntityClass() {
        return ScheduleApprover.class;
    }


    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<ScheduleApprover> getByScheduleId(Long id) {
        String sql = "FROM ScheduleApprover sch WHERE sch.scheduleId = ?";
        return getCurrentSession().createQuery(sql)
                .setParameter(0,id)
                .list();
    }

    @Override
    public List<ScheduleApprover> getScheduleListByUser(Long id) {

        return getCurrentSession().createQuery("FROM ScheduleApprover sch WHERE sch.userId = ?").setParameter(0,id).list();
    }

}
