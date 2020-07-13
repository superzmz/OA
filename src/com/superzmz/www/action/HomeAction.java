package com.superzmz.www.action;


import com.superzmz.www.bean.Notice;
import com.superzmz.www.service.NoticeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class HomeAction {
    @Resource
    private NoticeService noticeService ;
    @RequestMapping("/home_index.action")
    public String index() throws Exception {
        return "home/index";
    }
    @RequestMapping("/home_top.action")
    public String top() throws Exception {
        return "home/top";
    }
    @RequestMapping("/home_bottom.action")
    public String bottom() throws Exception {
        return "home/bottom";
    }
    @RequestMapping("/home_left.action")
    public String left() throws Exception {
        return "home/left";
    }
    @RequestMapping("/home_right.action")
    public String right(Map map) throws Exception {
        //准备公告信息
        List<Notice> noticeList = noticeService.findAll();
        if (noticeList.size() > 0) {
            Notice notice = noticeList.get(0);
            map.put("content", notice.getContent());
        }
        return "home/right";
    }
}

