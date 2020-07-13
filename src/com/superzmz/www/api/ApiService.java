package com.superzmz.www.api;

import com.superzmz.www.api.bean.PageBean;
import com.superzmz.www.api.bean.ResultBean;
import com.superzmz.www.bean.User;

import java.util.List;

public interface ApiService {

    ResultBean<PageBean<User>> getResultsByPage(Integer page);

    ResultBean<List<User>> getResultById(Integer id);
}
