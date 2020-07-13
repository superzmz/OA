package com.superzmz.www.service;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.bean.Timecard;
import com.superzmz.www.bean.User;

import java.util.List;

public interface TimecardService extends BaseDao<Timecard> {

    List<Timecard> findPersonTimecard(Long userId);

    List<User> findAllTimecard();
}
