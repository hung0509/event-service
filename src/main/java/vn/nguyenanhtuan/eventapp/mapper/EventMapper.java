
package vn.nguyenanhtuan.eventapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.nguyenanhtuan.eventapp.dto.request.EventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;
import vn.nguyenanhtuan.eventapp.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventReqDto req);

    EventResDto toEventResDto(Event event);
}
