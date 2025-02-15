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
import vn.nguyenanhtuan.eventapp.dto.request.AuthenticateRequest;
import vn.nguyenanhtuan.eventapp.dto.response.AuthenticateResponse;
import vn.nguyenanhtuan.eventapp.service.AuthenticateService;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticateController {
    AuthenticateService authenticateService;

    @PostMapping
    public ApiResponse<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest req){
        log.info("*log authentication by email and password*");
        return ApiResponse.<AuthenticateResponse>builder()
                .result(authenticateService.authenticate(req))
                .message("Login successfully!")
                .build();
    }
}
