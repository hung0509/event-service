package vn.nguyenanhtuan.eventapp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import vn.nguyenanhtuan.eventapp.dto.ApiResponse;
import vn.nguyenanhtuan.eventapp.dto.request.FacultyReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.FacultyResDto;
import vn.nguyenanhtuan.eventapp.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculties")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class FacultyController {
    FacultyService facultyService;

    @PostMapping
    public ApiResponse<FacultyResDto> save(@RequestBody FacultyReqDto req) {
        log.info("*Log table faculty. save faculty in db*");
        return ApiResponse.<FacultyResDto>builder()
                .message("Save faculty successfully!")
                .result(facultyService.save(req))
                .build();
    }

    @GetMapping
    public ApiResponse<List<FacultyResDto>> getAll() {
        log.info("*Log table faculty. save faculty in db*");
        return ApiResponse.<List<FacultyResDto>>builder()
                .message("Save faculty successfully!")
                .result(facultyService.getAll())
                .build();
    }
}
