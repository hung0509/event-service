package vn.nguyenanhtuan.eventapp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.nguyenanhtuan.eventapp.dto.ApiResponse;
import vn.nguyenanhtuan.eventapp.dto.request.CategoryReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.CategoryResDto;
import vn.nguyenanhtuan.eventapp.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResDto> save(@RequestBody CategoryReqDto req) {
        return ApiResponse.<CategoryResDto>builder()
                .message("Save category successfully")
                .result(categoryService.save(req))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResDto>> getAll() {
        return ApiResponse.<List<CategoryResDto>>builder()
                .message("Save category successfully")
                .result(categoryService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResDto> getById(@PathVariable int id) {
        return ApiResponse.<CategoryResDto>builder()
                .message("Save category successfully")
                .result(categoryService.getById(id))
                .build();
    }
}
