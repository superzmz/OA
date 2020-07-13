package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.Notice;
import com.superzmz.www.service.NoticeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends BaseDaoImpl<Notice> implements NoticeService {

    @Override
    public Class getEntityClass() {
        return Notice.class;
    }
}
