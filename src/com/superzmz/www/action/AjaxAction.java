package com.superzmz.www.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;

@Controller
@Scope("prototype")
public class AjaxAction {

    @RequestMapping("/ajax1.action")
    public void ajax(PrintWriter writer){
        String json = "{'name':'deeper','age':'20'}";
        writer.write(json);
        writer.flush();
        writer.close();
    }

    @ResponseBody
    @RequestMapping("/ajax2.action")
    public String ajax(){
        String jsonStr = "{\"name\": \"deeper\",\"email\": \"xx@qq.com\"}";
        return jsonStr;
    }



}
