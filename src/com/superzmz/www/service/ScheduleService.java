package com.superzmz.www.service;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.bean.Department;
import com.superzmz.www.bean.Schedule;
import com.superzmz.www.bean.User;

import java.util.List;

public interface ScheduleService extends BaseDao<Schedule> {

    List<Schedule> getScheduleListByUser(Long id);

    List<Schedule> getAllScheduleList();

    void submit(Schedule schedule, Department depart);

    List<User> getApproversByDepart(User user, Department department);
}
