package vn.nguyenanhtuan.eventapp.service;

import vn.nguyenanhtuan.eventapp.dto.ApiResponse;
import vn.nguyenanhtuan.eventapp.dto.request.CategoryReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.CategoryResDto;

import java.util.List;

public interface CategoryService {
    public CategoryResDto save(CategoryReqDto categoryReqDto);

    public List<CategoryResDto> getAll();

    public CategoryResDto getById(int id);
}
