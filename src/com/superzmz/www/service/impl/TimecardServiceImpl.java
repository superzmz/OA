package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.Timecard;
import com.superzmz.www.bean.User;
import com.superzmz.www.service.TimecardService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TimecardServiceImpl extends BaseDaoImpl<Timecard> implements TimecardService {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Class getEntityClass() {
        return Timecard.class;
    }

    @Override
    public List<Timecard> findPersonTimecard(Long userId) {
        List<Timecard> timecardList = getCurrentSession().createQuery("FROM Timecard t WHERE t.userId = ? ORDER BY t.createTime DESC")
                .setParameter(0,userId).list();
//        for (Timecard timecart:timecardList) {
//
//        }
        return timecardList;
    }

    @Override
    public List<User> findAllTimecard() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User";
        Query query = session.createQuery(hql);
        List<User> userList = query.list();
        for (User u:userList) {
            Long id = u.getId();
            int size = findPersonTimecard(id).size();
            String s =String.valueOf(size);
            u.setCount(s);
        }
        return userList;
    }


    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }


}
