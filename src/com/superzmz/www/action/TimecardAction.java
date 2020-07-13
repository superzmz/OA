package com.superzmz.www.action;

import com.superzmz.www.Constants;
import com.superzmz.www.bean.*;
import com.superzmz.www.service.*;
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
public class TimecardAction {
    @Resource
    private TimecardService timecardService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private UserService userService;
    @Resource
    private UserMgService userMgService;

    @RequestMapping("/timecardP_list.action")
    public String list(Integer currentPage, Map map, HttpSession session) throws Exception{
        // 分页显示
        if (currentPage == null ) {
            currentPage = Constants.PAGE_CURRENT_DEFAULT;
        }
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();
        map.put("userId",userId);
        List<Timecard> timecardList = timecardService.findPersonTimecard(userId);
        int count = 0;
        PageBean pageBean = timecardService.getTCPageBean(currentPage,Constants.PAGE_SIZE,count,timecardList);
        map.put("pageBean", pageBean);
        if(timecardList.size() != 0){
        Timecard timecard = timecardList.get(0);
        Date createTime = timecard.getCreateTime();
        Date date= new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd");
        String format = ft.format(createTime);
        String format1 = ft.format(date);
            Date nine = new Date();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(nine);
            // 将时分秒,毫秒域清零
            cal1.set(Calendar.HOUR_OF_DAY, 12);
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.SECOND, 0);
            cal1.set(Calendar.MILLISECOND, 0);
            Date date1 = cal1.getTime();
         String format2 = ft.format(date1);

        if (format.compareTo(format1) == 0){
            int disable = 0;
            map.put("disable",disable);
        }else {
            if (date.before(date1)){
            int disable = 1;
            map.put("disable",disable);
            }else {
                int disable = 2;

                map.put("disable",disable);

            }
               }

    }else if(timecardList.size() == 0){
            int disable = 1;
            map.put("disable",disable);
        }
        return "timecard/personList";
    }

    @RequestMapping("/timecardA_list.action")
    public String allList(Integer currentPage, Map map, HttpSession session) throws Exception{
        // 分页显示
        if (currentPage == null ) {
            currentPage = Constants.PAGE_CURRENT_DEFAULT;
        }
        List<User> userList = timecardService.findAllTimecard();
        int count = 0;
        PageBean pageBean = userMgService.getATCPageBean(currentPage,Constants.PAGE_SIZE,count,userList);
        map.put("pageBean", pageBean);
        return "timecard/allList";
    }

    @RequestMapping("/timecard_timeCardUI.action")
    public String timeCardUI(Long userId,Map map){

        // 准备回显的数据
        User user = userMgService.getById(userId);
        Long id = user.getDepartment().getId();
        String departmentName = departmentService.getById(id).getName();
        map.put("departmentName",departmentName);
        map.put("user", user);
        return "timecard/saveUI";
    }
    @RequestMapping("timecard_timeCard.action")
    public String timeCard(Timecard model, HttpSession session, HttpServletRequest request){
        User user = (User) session.getAttribute("user");
        Long userId = user.getId();
        model.setUserId(userId);
        Date date = new Date();
        model.setCreateTime(date);
        Date nine = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(nine);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 9);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        if (date.before(cal1.getTime())){
            model.setType(Constants.TIME_CARD_TYPE);
        }else {
            model.setType(Constants.TIME_CARD_TYPE1);
        }
        model.setUsername(user.getName());
        model.setLoginName(user.getLoginName());
        String departmentName = request.getParameter("departmentName");
        model.setDepartmentName(departmentName);
        timecardService.save(model);
        return "redirect:/timecardP_list.action";
    }
    private User getCurrentUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user;
    }
}
