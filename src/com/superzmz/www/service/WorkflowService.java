package com.superzmz.www.service;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.bean.Department;
import com.superzmz.www.bean.LeaveBean;
import com.superzmz.www.bean.Role;
import com.superzmz.www.bean.User;

import java.util.List;

public interface WorkflowService extends BaseDao<LeaveBean>{
    /**
     * 提交申请：
     *
     * 1，保存申请信息
     *
     * 2，启动流程开始流转
     *
     */
    void submit(LeaveBean leave, Department depart);

    List<LeaveBean> getLeaveListByUser(Long id);

    List<User> getApproversByDepart(User user, Department department);

}
