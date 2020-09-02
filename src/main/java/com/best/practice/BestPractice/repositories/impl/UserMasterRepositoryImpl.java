package com.best.practice.BestPractice.repositories.impl;


import com.best.practice.BestPractice.entities.UserMasterEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class UserMasterRepositoryImpl implements UserMasterRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserMasterEntity findByUserName(String name) {
        Query query = entityManager.createNativeQuery("select * from user_master where name = " + name, UserMasterEntity.class);
        return (UserMasterEntity) query.getSingleResult();
    }

}
