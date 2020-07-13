package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.Department;
import com.superzmz.www.bean.Schedule;
import com.superzmz.www.bean.Timecard;
import com.superzmz.www.bean.User;
import com.superzmz.www.service.ScheduleService;
import com.superzmz.www.service.TimecardService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl extends BaseDaoImpl<Schedule> implements ScheduleService {
    @Resource
    private SessionFactory sessionFactory;
    @Resource
    private TimecardService timecardService;

    @Override
    public Class getEntityClass() {
        return Schedule.class;
    }


    @Override
    public List getScheduleListByUser(Long id) {
        return getCurrentSession().createQuery("FROM Schedule sch WHERE sch.userId = ?").setParameter(0,id).list();
    }

    @Override
    public List<Schedule> getAllScheduleList() {
        return getCurrentSession().createQuery("FROM Schedule").list();
    }

    @Override
    public void submit(Schedule schedule, Department depart) {
        schedule.setStatus(Schedule.STATUS_NOMAL);
        schedule.setUpdateTime(new Date());
        update(schedule);
    }

    @Override
    public List<User> getApproversByDepart(User user, Department department) {
        List<User> userList= getCurrentSession().createQuery("FROM User u WHERE u.department = ? AND u!= ?")
                .setParameter(0, department)
                .setParameter(1, user)
                .list();
        List<User> userList1 = new ArrayList<User>();

        for (User user1:userList){
            Long user1Id = user1.getId();
            boolean b = isTimecard(user1Id);
            int haswork = user1.getHaswork();
            if(haswork == 0 && b == true){
                userList1.add(user1);
            }
            }
        return userList1;
    }

    public boolean isTimecard(Long userId){
        List<Timecard> timecardList = timecardService.findPersonTimecard(userId);
        boolean info = false;
            if (timecardList.size()!=0){
            Timecard timecard = timecardList.get(0);
            Date createTime = timecard.getCreateTime();
            Date date= new Date();
            SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd");
            String format = ft.format(createTime);
            String format1 = ft.format(date);
            if(format.compareTo(format1) == 0){
                info = true;
            }else {
                info = false;
            }
            }
        return info;
    }

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
