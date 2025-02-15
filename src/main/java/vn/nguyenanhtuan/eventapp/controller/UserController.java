package vn.nguyenanhtuan.eventapp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nguyenanhtuan.eventapp.dto.ApiResponse;
import vn.nguyenanhtuan.eventapp.dto.request.UserRequestDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;
import vn.nguyenanhtuan.eventapp.service.UserService;

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
}
