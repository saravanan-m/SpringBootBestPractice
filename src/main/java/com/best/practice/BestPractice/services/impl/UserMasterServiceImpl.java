package com.best.practice.BestPractice.services.impl;

import com.best.practice.BestPractice.dtos.UserMasterDto;
import com.best.practice.BestPractice.entities.AccountEntity;
import com.best.practice.BestPractice.entities.UserMasterEntity;
import com.best.practice.BestPractice.exception.ResourceExist;
import com.best.practice.BestPractice.exception.ResourceNotFound;
import com.best.practice.BestPractice.mappers.AccountMapper;
import com.best.practice.BestPractice.mappers.UserMasterMapper;
import com.best.practice.BestPractice.repositories.AccountRepository;
import com.best.practice.BestPractice.repositories.UserMasterRepository;
import com.best.practice.BestPractice.services.UserMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserMasterServiceImpl implements UserMasterService {

    @Autowired
    UserMasterRepository userMasterRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public UserMasterDto addUser(UserMasterDto userMasterDto) throws ResourceExist {
        UserMasterEntity entity = userMasterRepository.getUserByName(userMasterDto.getName());
        if (entity != null) {
            throw new ResourceExist("User name exist");
        }
        entity = UserMasterMapper.INSTANCE.toEntity(userMasterDto);
        entity.setPassword(bcryptEncoder.encode(entity.getPassword()));
        entity = userMasterRepository.save(entity);

        AccountEntity accountEntity = AccountMapper.INSTANCE.toEntity(userMasterDto.getAccount());
        accountEntity.setUserMasterEntity(entity);
        accountEntity = accountRepository.save(accountEntity);

        userMasterDto = UserMasterMapper.INSTANCE.toDto(entity);
        userMasterDto.setAccount(AccountMapper.INSTANCE.toDto(accountEntity));

        return userMasterDto;
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
