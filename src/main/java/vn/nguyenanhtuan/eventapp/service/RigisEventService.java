package vn.nguyenanhtuan.eventapp.service;


import vn.nguyenanhtuan.eventapp.dto.request.RegisEventDto;
import vn.nguyenanhtuan.eventapp.dto.request.RegisEventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;

import java.util.List;

public interface RigisEventService {
    List<EventResDto> getEventHaveFilter(RegisEventReqDto req);

    Integer registerEvent(RegisEventDto req);

    List<UserResponseDto> getUserByEventId(int id);
}
