package vn.nguyenanhtuan.eventapp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.nguyenanhtuan.eventapp.dto.ApiResponse;
import vn.nguyenanhtuan.eventapp.dto.request.UserRequestDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;
import vn.nguyenanhtuan.eventapp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping()
    public ApiResponse<UserResponseDto> createUser(@RequestBody UserRequestDto req) {
        log.info("*Log table user. --call api save user-- *");
        return ApiResponse.<UserResponseDto>builder()
                .message("Save user successfully!")
                .result(userService.save(req))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<UserResponseDto>> getAll(){
        log.info("*Log table user. --call api get all user-- *");
        return ApiResponse.<List<UserResponseDto>>builder()
                .message("Get users succesfully!")
                .result(userService.getAll())
                .build();
    }
    @GetMapping("/role/{name}")
    public ApiResponse<List<UserResponseDto>> getAllByRole(@PathVariable String name){
        log.info("*Log table user. --call api get all user-- *");
        return ApiResponse.<List<UserResponseDto>>builder()
                .message("Get users succesfully!")
                .result(userService.getByRole(name))
                .build();
    }
}
