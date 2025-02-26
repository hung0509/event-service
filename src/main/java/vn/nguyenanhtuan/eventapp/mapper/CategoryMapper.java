package vn.nguyenanhtuan.eventapp.mapper;

import org.mapstruct.Mapper;
import vn.nguyenanhtuan.eventapp.dto.request.CategoryReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.CategoryResDto;
import vn.nguyenanhtuan.eventapp.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryReqDto req);
    CategoryResDto toCategoryResDto(Category category);
}
