package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.*;
import com.superzmz.www.service.UserService;
import com.superzmz.www.service.WorkflowService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class WorkflowServiceImpl extends BaseDaoImpl<LeaveBean> implements WorkflowService {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Class getEntityClass() {
        return LeaveBean.class;
    }


    @Override
    public void submit(LeaveBean leave, Department depart) {
        leave.setStatus(LeaveBean.STATUS_NOMAL);
        leave.setUpdateTime(new Date());
        update(leave);

    }


    @Override
    public List<LeaveBean> getLeaveListByUser(Long id) {
        return getCurrentSession()
                .createQuery("FROM LeaveBean lea WHERE lea.userId = ?")
                .setParameter(0,id)
                .list();

    }

    //获取审批人(同部门 且不包含自己 )
    @Override
    public List<User> getApproversByDepart(User user, Department department) {
        List<User> users= getCurrentSession().createQuery("FROM User u WHERE u.department = ? AND u!= ?")
                .setParameter(0, department)
                .setParameter(1, user)
                .list();
        List<User> userList = new ArrayList<User>();
        for (User user1:users) {
            if (IsManager((long) 1,user1)){
                userList.add(user1);
            }
        }
        return userList;
    }

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    public boolean IsManager(Long id, User user){
        for (Role role : user.getRoles()) {
                if (role.getId().equals(id)) {
                    return true;
                }
        }
        return false;
    }
}

