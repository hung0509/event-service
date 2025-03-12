package vn.nguyenanhtuan.eventapp.service;

import vn.nguyenanhtuan.eventapp.dto.request.FacultyUpdateReq;
import vn.nguyenanhtuan.eventapp.dto.request.UserRequestDto;
import vn.nguyenanhtuan.eventapp.dto.request.UserUpdateReq;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;

import java.util.List;

public interface UserService{
    public UserResponseDto save(UserRequestDto req);

    public List<UserResponseDto> getAll();

    public List<UserResponseDto> getByRole(String role);

    public UserResponseDto update(UserUpdateReq req, int id);

    public UserResponseDto updateFaculty(FacultyUpdateReq req, int id);

    public UserResponseDto getUserById(int id);

    public UserResponseDto registerFaculty(UserRequestDto req);
}
