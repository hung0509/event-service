package vn.nguyenanhtuan.eventapp.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.nguyenanhtuan.eventapp.constant.ErrorCode;
import vn.nguyenanhtuan.eventapp.dto.ApiResponse;
import vn.nguyenanhtuan.eventapp.dto.request.CategoryReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.CategoryResDto;
import vn.nguyenanhtuan.eventapp.entity.Category;
import vn.nguyenanhtuan.eventapp.handle.GlobalException;
import vn.nguyenanhtuan.eventapp.mapper.CategoryMapper;
import vn.nguyenanhtuan.eventapp.reposiroty.CategoryRepository;
import vn.nguyenanhtuan.eventapp.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public CategoryResDto save(CategoryReqDto req) {
        Category category = categoryMapper.toCategory(req);

        return categoryMapper.toCategoryResDto( categoryRepository.save(category));
    }

    @Override
    public List<CategoryResDto> getAll() {
        var list = categoryRepository.findAll();
        return list.stream().map(categoryMapper::toCategoryResDto).toList();
    }

    @Override
    public CategoryResDto getById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.CATEGORY_NOT_EXIST));

        return categoryMapper.toCategoryResDto(category);
    }


}
