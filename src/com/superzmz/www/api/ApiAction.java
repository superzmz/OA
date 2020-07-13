package com.superzmz.www.api;

import com.google.gson.Gson;
import com.superzmz.www.api.bean.PageBean;
import com.superzmz.www.api.bean.ResultBean;
import com.superzmz.www.bean.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope("prototype")
public class ApiAction {

    @Resource
    private ApiService apiService;

    @RequestMapping(value="/api/user/{id}", method= RequestMethod.GET)
    public String add(@PathVariable("id") Integer id){
        ResultBean<List<User>> resultBean = apiService.getResultById(id);
        String responseJson = new Gson().toJson(resultBean);
        return responseJson ;
    }
    @RequestMapping(value="/api/user/{page}", method= RequestMethod.GET)
    public String list(@PathVariable("page") Integer page){
        // Android客户端的获取 ResultBean<PageBean<T>> resultBean = new Gson().fromJson(responseJson , getType());
        ResultBean<PageBean<User>> resultBean = apiService.getResultsByPage(page);
        String responseJson  = new Gson().toJson(resultBean);
        return responseJson ;
    }



}
