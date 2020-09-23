package com.best.practice.BestPractice.mappers;

import com.best.practice.BestPractice.dtos.AccountDto;
import com.best.practice.BestPractice.entities.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountEntity toEntity(AccountDto accountDto);
    AccountDto toDto(AccountEntity accountEntity);
}
