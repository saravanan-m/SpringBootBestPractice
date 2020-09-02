package com.best.practice.BestPractice.repositories.impl;

import com.best.practice.BestPractice.entities.UserMasterEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserMasterRepositoryCustom {

    UserMasterEntity findByUserName(String userName);

}
