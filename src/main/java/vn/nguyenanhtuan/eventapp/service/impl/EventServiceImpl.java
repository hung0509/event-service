package vn.nguyenanhtuan.eventapp.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.nguyenanhtuan.eventapp.constant.ErrorCode;
import vn.nguyenanhtuan.eventapp.constant.Status;
import vn.nguyenanhtuan.eventapp.dto.request.EventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;
import vn.nguyenanhtuan.eventapp.entity.Event;
import vn.nguyenanhtuan.eventapp.handle.GlobalException;
import vn.nguyenanhtuan.eventapp.mapper.EventMapper;
import vn.nguyenanhtuan.eventapp.reposiroty.EventRepository;
import vn.nguyenanhtuan.eventapp.service.EventService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventServiceImpl implements EventService {
    EventRepository eventRepository;
    EventMapper eventMapper;

    @Override
    public EventResDto save(EventReqDto eventReqDto) {


        return eventMapper.toEventResDto(eventRepository.save(null));
    }

    @Override
    public List<EventResDto> getEventByStatus(String status) {
        List<Event> list = null;
        if(status.equalsIgnoreCase("pending"))
            list = eventRepository.findAllByStatus(Status.PENDING.name());
        else if(status.equalsIgnoreCase("approved"))
            list = eventRepository.findAllByStatus(Status.APPROVE.name());
        else if(status.equalsIgnoreCase("reject"))
            list = eventRepository.findAllByStatus(Status.REJECT.name());
        else
            throw new GlobalException(ErrorCode.STATUS_NOT_EXIST);

        return list.stream().map(eventMapper::toEventResDto).toList();
    }

    @Override
    public EventResDto getById(int id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.EVENT_NOT_EXIST));
        return eventMapper.toEventResDto(event);
    }

    @Override
    public List<EventResDto> getAll(){
        var list = eventRepository.findAll();
        return list.stream().map(eventMapper::toEventResDto).toList();
    }
}
