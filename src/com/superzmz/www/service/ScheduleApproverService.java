package com.superzmz.www.service;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.bean.Schedule;
import com.superzmz.www.bean.ScheduleApprover;

import java.util.List;

public interface ScheduleApproverService extends BaseDao<ScheduleApprover> {
    List<ScheduleApprover> getByScheduleId(Long id);
    List<ScheduleApprover> getScheduleListByUser(Long id);
}
