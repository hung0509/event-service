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
import vn.nguyenanhtuan.eventapp.dto.request.RoleReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.RoleResDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;
import vn.nguyenanhtuan.eventapp.service.RoleService;

@RestController
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResDto> save(@RequestBody RoleReqDto req){
        log.info("*Log table role. --call api save role-- *");
        return ApiResponse.<RoleResDto>builder()
                .message("Save role successfully!")
                .result(roleService.save(req))
                .build();
    }
}
