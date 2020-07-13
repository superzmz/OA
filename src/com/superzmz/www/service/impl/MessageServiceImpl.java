package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.Message;
import com.superzmz.www.bean.Schedule;
import com.superzmz.www.bean.ScheduleApprover;
import com.superzmz.www.bean.User;
import com.superzmz.www.service.MessageService;
import com.superzmz.www.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl extends BaseDaoImpl<Message> implements MessageService {

    @Resource
    private UserService userService;

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Class getEntityClass() {
        return Message.class;
    }


    @Override
    public List<Message> getMessageList(User currentUser,boolean disable) {

        return getCurrentSession()
                .createQuery("FROM Message m WHERE m.userId = ? AND m.disable = ?")
                .setParameter(0, currentUser.getId())
                .setParameter(1,disable)  // 仅仅显示 未审批的消息
                .list();
    }

    //发送Message消息
    @Override
    public void sendMessage(Long leaveId,String title,String...userIds){
        //发送邮件
        Message message = null;
        // 添加Message 表记录
        for (String userid : userIds) {
            if (!"".equals(userid) && null != userid) {
                message = new Message();
                message.setTitle(title);
                message.setCreateTime(new Date());
                message.setWatch(Message.MESSAGE_NO_WATCHED);
                message.setType(Message.MESSAGE_TYPE_TASK);

                message.setLeaveId(leaveId);
                message.setUserId(Long.parseLong(userid));

                getCurrentSession().save(message);

            }
        }

    }

    @Override
    public void sendWorkMessage(Long scheduleId, String title, String... userIds) {
        //发送邮件
        Message message = null;
//        ScheduleApprover scheduleApprover = null;
        // 添加Message 表记录
        for (String userid : userIds) {
            if (!"".equals(userid) && null != userid) {
                message = new Message();
                message.setTitle(title);
                message.setCreateTime(new Date());
                message.setWatch(Message.MESSAGE_NO_WATCHED);
                message.setType(Message.MESSAGE_TYPE_TASK);
                message.setScheduleId(scheduleId);
                message.setUserId(Long.parseLong(userid));
//                scheduleApprover.setCreateTime(message.getCreateTime());
//                scheduleApprover.setScheduleId(message.getScheduleId());
//                scheduleApprover.setStatus(Schedule.STATUS_NOMAL);
//                scheduleApprover.setUserId(message.getUserId());
//                User user = userService.getById(message.getUserId());
//                scheduleApprover.setUsername(user.getName());
                getCurrentSession().save(message);
//                getCurrentSession().save(scheduleApprover);
            }
        }

    }


    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

}
