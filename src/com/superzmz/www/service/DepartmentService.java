package com.superzmz.www.service;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.bean.Department;

import java.util.List;

public interface DepartmentService extends BaseDao<Department>{

    List<Department> findTopList();

    List<Department> findChildren(Long parentId);

}
