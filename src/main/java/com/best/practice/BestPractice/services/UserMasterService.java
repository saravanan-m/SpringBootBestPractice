package com.best.practice.BestPractice.services;


import com.best.practice.BestPractice.dtos.UserMasterDto;
import com.best.practice.BestPractice.exception.ResourceExist;
import com.best.practice.BestPractice.exception.ResourceNotFound;

import java.util.List;

public interface UserMasterService {
    UserMasterDto addUser(UserMasterDto userMasterDto) throws ResourceExist;

    UserMasterDto getUserByName(String name) throws ResourceNotFound;

    List<UserMasterDto> getUsers();
}
