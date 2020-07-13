package com.superzmz.www.service;

import com.superzmz.www.base.BaseDao;
import com.superzmz.www.bean.Photo;

import java.util.List;

public interface UploadService extends BaseDao<Photo>{

    List<Photo> findOwnPhoto(Long id);

}
