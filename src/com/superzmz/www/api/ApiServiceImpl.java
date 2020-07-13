package com.superzmz.www.api;

import com.superzmz.www.api.bean.PageBean;
import com.superzmz.www.api.bean.ResultBean;
import com.superzmz.www.bean.User;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ApiServiceImpl implements ApiService{

    @Override
    public ResultBean<PageBean<User>> getResultsByPage(Integer page) {
        return null;
    }

    @Override
    public ResultBean<List<User>> getResultById(Integer id) {
        return null;
    }
}
