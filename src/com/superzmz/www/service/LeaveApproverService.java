package com.superzmz.www.service;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.bean.LeaveApprover;

import java.util.List;

public interface LeaveApproverService extends BaseDao<LeaveApprover>{


    List<LeaveApprover> getByLeaveId(Long id);
}
