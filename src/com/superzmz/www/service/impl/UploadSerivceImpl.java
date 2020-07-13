package com.superzmz.www.service.impl;

import com.superzmz.www.base.BaseDaoImpl;
import com.superzmz.www.bean.Photo;
import com.superzmz.www.service.UploadService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class UploadSerivceImpl extends BaseDaoImpl<Photo> implements UploadService {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Class getEntityClass() {
        return Photo.class;
    }


    @Override
    public List<Photo> findOwnPhoto(Long id) {
        return getCurrentSession().createQuery("FROM Photo p WHERE p.userId = ?")
                .setParameter(0, id)
                .list();
    }

    public Session getCurrentSession(){
        return  sessionFactory.getCurrentSession();
    }

}
