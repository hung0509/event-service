package vn.nguyenanhtuan.eventapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.nguyenanhtuan.eventapp.dto.request.UserRequestDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;
import vn.nguyenanhtuan.eventapp.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role",ignore = true)
    @Mapping(target = "password", ignore = true)
    User toUser(UserRequestDto req);
    UserResponseDto toUserResponseDto(User user);
}
