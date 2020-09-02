package com.best.practice.BestPractice.mappers;

import com.best.practice.BestPractice.dtos.UserMasterDto;
import com.best.practice.BestPractice.entities.UserMasterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMasterMapper {
    UserMasterMapper INSTANCE = Mappers.getMapper(UserMasterMapper.class);

    UserMasterEntity toEntity(UserMasterDto userMasterDto);
    UserMasterDto toDto(UserMasterEntity userMasterEntity);
}
