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
import vn.nguyenanhtuan.eventapp.entity.Faculty;
import vn.nguyenanhtuan.eventapp.handle.GlobalException;
import vn.nguyenanhtuan.eventapp.mapper.EventMapper;
import vn.nguyenanhtuan.eventapp.reposiroty.EventRepository;
import vn.nguyenanhtuan.eventapp.reposiroty.FacultyRepository;
import vn.nguyenanhtuan.eventapp.service.EventService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventServiceImpl implements EventService {
    EventRepository eventRepository;
    EventMapper eventMapper;
    FacultyRepository facultyRepository;

    @Override
    public EventResDto save(EventReqDto eventReqDto) {
        Event event = eventMapper.toEvent(eventReqDto);

        Faculty faculty = facultyRepository.findById(eventReqDto.getFacultyId())
                .orElseThrow(() -> new GlobalException(ErrorCode.FACULTY_NOT_EXIST));

        event.setFaculty(faculty);

        return eventMapper.toEventResDto(eventRepository.save(event));
    }

    @Override
    public List<EventResDto> getEventByStatus(String status) {
        List<Event> list = null;
        if(status.equalsIgnoreCase("pending"))
            list = eventRepository.findAllByStatus(Status.PENDING.name());
        else if(status.equalsIgnoreCase("approved"))
            list = eventRepository.findAllByStatus(Status.APPROVE.name());

//        List<EventResDto> data = new ArrayList<>();
//
//        for(Event event : list){
//            EventResDto eventResDto = eventMapper.toEventResDto(event);
//            eventResDto.setFacultyName(event.getFaculty().getFacultyName());
//            eventResDto.setFacultyLogo(event.getFaculty().getFacultyLogo());
//
//            data.add(eventResDto);
//        }
//
//        return data;

        return list.stream().map(eventMapper::toEventResDto).toList();
    }

    @Override
    public EventResDto getById(int id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.EVENT_NOT_EXIST));
        return eventMapper.toEventResDto(event);
    }
}
