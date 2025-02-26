package vn.nguyenanhtuan.eventapp.service;

import vn.nguyenanhtuan.eventapp.dto.request.UserRequestDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;

import java.util.List;

public interface UserService{
    public UserResponseDto save(UserRequestDto req);

    public List<UserResponseDto> getAll();

    public List<UserResponseDto> getByRole(String role);
}
