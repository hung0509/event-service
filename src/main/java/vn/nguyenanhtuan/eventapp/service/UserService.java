package vn.nguyenanhtuan.eventapp.service;

import vn.nguyenanhtuan.eventapp.dto.request.UserRequestDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;

public interface UserService{
    public UserResponseDto save(UserRequestDto req);
}
