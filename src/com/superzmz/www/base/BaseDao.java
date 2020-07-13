package com.superzmz.www.base;

import com.superzmz.www.bean.PageBean;
import com.superzmz.www.bean.Timecard;
import com.superzmz.www.bean.User;

import java.util.List;




public interface BaseDao<T> {
    void save(T entity);

    void delete(Long id);

    void update(T entity);

    T getById(Long id);

    List<T> getByIds(Long[] ids);

    List<T> findAll();

    Class getEntityClass();

    // 公共的查询分页信息的方法
    PageBean getPageBean(int currentPage, int pageSize, String hql, List<Object> parameters);

    //打卡记录分页
    PageBean getTCPageBean(int currentPage, int pageSize,int count,List<Timecard> timecardList);

    PageBean getATCPageBean(int currentPage, int pageSize,int count,List<User> userList);


}
