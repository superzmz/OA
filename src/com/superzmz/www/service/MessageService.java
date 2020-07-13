package com.superzmz.www.service;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.bean.Message;
import com.superzmz.www.bean.User;

import java.util.List;


public interface MessageService extends BaseDao<Message>{

    /**
     * 查询我的消息列表
     *
     * @param currentUser
     * @return
     */
    List<Message> getMessageList(User currentUser, boolean disable);

    void sendMessage(Long leaveId, String title, String... ids);

    void sendWorkMessage(Long scheduleId, String title, String... ids);

}
