package com.best.practice.BestPractice.services.impl;

import com.best.practice.BestPractice.dtos.UserMasterDto;
import com.best.practice.BestPractice.entities.UserMasterEntity;
import com.best.practice.BestPractice.exception.ResourceExist;
import com.best.practice.BestPractice.exception.ResourceNotFound;
import com.best.practice.BestPractice.mappers.UserMasterMapper;
import com.best.practice.BestPractice.repositories.UserMasterRepository;
import com.best.practice.BestPractice.services.UserMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserMasterServiceImpl implements UserMasterService {

    @Autowired
    UserMasterRepository userMasterRepository;


    @Override
    public UserMasterDto addUser(UserMasterDto userMasterDto) throws ResourceExist {
        UserMasterEntity entity = userMasterRepository.getUserByName(userMasterDto.getName());
        if (entity != null) {
            throw new ResourceExist("User name exist");
        }
        entity = UserMasterMapper.INSTANCE.toEntity(userMasterDto);
        entity = userMasterRepository.save(entity);

        return UserMasterMapper.INSTANCE.toDto(entity);
    }

    @Override
    public UserMasterDto getUserByName(String name) throws ResourceNotFound {
        return Optional.ofNullable(userMasterRepository
                .getUserByName(name))
                .map(UserMasterMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }

    @Override
    public List<UserMasterDto> getUsers() {
        return Optional.ofNullable(userMasterRepository.findAll())
                .orElse(Collections.emptyList())
                .stream()
                .map(UserMasterMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
