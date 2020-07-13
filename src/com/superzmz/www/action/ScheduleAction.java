package com.superzmz.www.action;

import com.superzmz.www.bean.*;
import com.superzmz.www.service.MessageService;
import com.superzmz.www.service.ScheduleApproverService;
import com.superzmz.www.service.ScheduleService;
import com.superzmz.www.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
public class ScheduleAction {

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private ScheduleApproverService scheduleApproverService;

    @Resource
    private UserService userService;

    @Resource
    private MessageService messageService ;

    @RequestMapping("/taskUI.action")
    public String taskUI(Map map,HttpSession session) throws Exception{

        Date currentDate = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(currentDate);
        map.put("currentDate", date);

        User user = getCurrentUser(session);
        List<User> staffs = scheduleService.getApproversByDepart(user, user.getDepartment());
        if (staffs.size() != 0){
        for (User staff : staffs) {

            Set<Role> roles  = staff.getRoles();
            StringBuffer stringBuffer = new StringBuffer();
            for (Role r : roles) {
                stringBuffer.append(r.getName()+",");
            }
            String rolesName  =  stringBuffer.toString();
            staff.setRolesName(rolesName.substring(0,rolesName.length()-1));
        }
        map.put("staffs",staffs);
        }
        return "schedule/task";
    }

    @RequestMapping("/task.action")
    public String task(Schedule schedule, HttpSession session, HttpServletRequest request) throws Exception{
        // 封装申请信息
        User user = getCurrentUser(session);
        schedule.setCreateTime(new Date());
        schedule.setUserId(user.getId());
        scheduleService.save(schedule);
        scheduleService.submit(schedule,user.getDepartment());
        String staffid = request.getParameter("staffid");
        schedule.setStaffId(staffid);
        //发送通知
        String staffStr = schedule.getStaffId();
        String staffs [] = staffStr.split(",");

        messageService.sendWorkMessage(schedule.getId(),user.getName() + "发来工作任务",staffs);

        for (String userid : staffs) {
            if (!"".equals(userid) && null != userid) {
                int i = 1;
                long id = Long.parseLong(userid);
                User user1 = userService.getById(id);
                user1.setHaswork(i);
                userService.update(user1);
            }
        }

        return "redirect:/watchinglist.action";
    }

    @RequestMapping("/myWorkLog.action")
    public String myWorkLog(HttpSession session,Map map) throws Exception{
        List<Schedule> list = scheduleService.getAllScheduleList();
        map.put("scheduleList",list);
        return "schedule/myWorkLog";
    }

    @RequestMapping("/watchinglist.action")
    public String watching(HttpSession session,Map map) throws Exception{
        List<Schedule> allScheduleList = scheduleService.getAllScheduleList();
        map.put("allScheduleList",allScheduleList);
        return "schedule/watchinglist";
    }

    @RequestMapping("/scheduleDetail.action")
    public String scheduleDetail(Long scheduleId,Map map) throws Exception{
        Schedule schedule = scheduleService.getById(scheduleId);
        Long userId = schedule.getUserId();
        User user = userService.getById(userId);
        map.put("name",user.getName());
        List<ScheduleApprover> scheduleApprovers = scheduleApproverService.getByScheduleId(schedule.getId());
        User scheduleUser = userService.getById(schedule.getUserId());
        map.put("scheduleApprovers",scheduleApprovers);
        map.put("schedule",schedule);
        map.put("scheduleUser",scheduleUser);
        return "schedule/scheduleDetail";
    }

    @RequestMapping("/schedule_myMessageList.action")
    public String myMessageList(Map map,boolean disable,HttpSession session) throws Exception {

        List<Message> messagesList = messageService.getMessageList(getCurrentUser(session),disable);
        map.put("messageList", messagesList);
        return "schedule/myMessageList";
    }

    @RequestMapping("/schedule_approveUI.action")
    public String approveUI(Long messageId,Map map) throws Exception{
        Message message = messageService.getById(messageId);
        Long scheduleId = message.getScheduleId();
        Schedule schedule = scheduleService.getById(scheduleId);
        Long userId = schedule.getUserId();
        User approverUser = userService.getById(userId);
        map.put("message", message);
        map.put("schedule",schedule);
        map.put("approverUser",approverUser);
        return "schedule/approveUI";
    }
    @RequestMapping("/schedule_approve.action")
    public String approve(HttpSession session,Long messageId,ScheduleApprover model) throws Exception{
        User currentUser = getCurrentUser(session);

        // 消息处理
        Message message = messageService.getById(messageId);
        message.setDisable(true);
        messageService.update(message);
        model.setUserId(currentUser.getId());
        model.setUsername(currentUser.getName());
        model.setCreateTime(new Date());
        scheduleApproverService.save(model);
        Long scheduleId = model.getScheduleId();
        Schedule schedule = scheduleService.getById(scheduleId);
        int status1 = model.getStatus();
        if (status1 == 1){
            currentUser.setHaswork(0);
            userService.update(currentUser);
        }
        if(model.getStatus() == ScheduleApprover.STATUS_NOT_DONE){
            schedule.setResult(Schedule.STATUS_NOT_DONE);
            scheduleService.update(schedule);
        }else {
            int sendRequestLength = schedule.getStaffId().split(",").length-1;
            List<ScheduleApprover> scheduleApprovers = scheduleApproverService.getByScheduleId(schedule.getId());
            if(scheduleApprovers.size() == sendRequestLength){
                List<Integer> tempResults = new ArrayList<Integer>();
                for (ScheduleApprover scheduleApprover:scheduleApprovers) {
                    int status = scheduleApprover.getStatus();
                    tempResults.add(status);
                }
                if (tempResults.contains(ScheduleApprover.STATUS_NOT_DONE)){
                    schedule.setResult(Schedule.STATUS_NOT_DONE);
                    scheduleService.update(schedule);
                }else {
                    schedule.setResult(Schedule.STATUS_DONE);
                    scheduleService.update(schedule);
                }
            }
        }
        return "redirect:/schedule_myMessageList.action";
    }


    private User getCurrentUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user;
    }
}
