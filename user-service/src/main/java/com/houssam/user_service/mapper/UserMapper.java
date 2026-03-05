package com.houssam.user_service.mapper;

import com.houssam.user_service.dto.UserRequestDto;
import com.houssam.user_service.dto.UserResponseDto;
import com.houssam.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toEntity(UserRequestDto dto);

    UserResponseDto toResponseDto(User user);
}
