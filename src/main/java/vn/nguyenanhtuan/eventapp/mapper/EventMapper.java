
package vn.nguyenanhtuan.eventapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.nguyenanhtuan.eventapp.dto.request.EventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;
import vn.nguyenanhtuan.eventapp.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "bannerUrl", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    Event toEvent(EventReqDto req);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryName")
    EventResDto toEventResDto(Event event);
}
