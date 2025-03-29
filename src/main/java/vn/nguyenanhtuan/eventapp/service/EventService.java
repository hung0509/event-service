package vn.nguyenanhtuan.eventapp.service;

import vn.nguyenanhtuan.eventapp.dto.request.EventReqDto;
import vn.nguyenanhtuan.eventapp.dto.request.RegisEventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.DashBoardDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface EventService {
    public EventResDto save(EventReqDto eventReqDto) throws IOException, GeneralSecurityException;

    public List<EventResDto> getEventByStatus(String status);

    public EventResDto getById(int id);

    public List<EventResDto> getAll(RegisEventReqDto req);

    public EventResDto update(String status, int id, String comment);

    public DashBoardDto getDashBoard();
}
