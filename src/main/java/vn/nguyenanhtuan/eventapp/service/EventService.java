package vn.nguyenanhtuan.eventapp.service;

import vn.nguyenanhtuan.eventapp.dto.request.EventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;

import java.util.List;

public interface EventService {
    public EventResDto save(EventReqDto eventReqDto);

    public List<EventResDto> getEventByStatus(String status);

    public EventResDto getById(int id);

    public List<EventResDto> getAll();
}
