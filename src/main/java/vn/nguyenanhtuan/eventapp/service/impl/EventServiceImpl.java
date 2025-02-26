package vn.nguyenanhtuan.eventapp.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.nguyenanhtuan.eventapp.constant.ErrorCode;
import vn.nguyenanhtuan.eventapp.constant.Status;
import vn.nguyenanhtuan.eventapp.dto.request.EventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;
import vn.nguyenanhtuan.eventapp.entity.Category;
import vn.nguyenanhtuan.eventapp.entity.Event;
import vn.nguyenanhtuan.eventapp.handle.GlobalException;
import vn.nguyenanhtuan.eventapp.mapper.EventMapper;
import vn.nguyenanhtuan.eventapp.reposiroty.CategoryRepository;
import vn.nguyenanhtuan.eventapp.reposiroty.EventRepository;
import vn.nguyenanhtuan.eventapp.service.EventService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventServiceImpl implements EventService {
    EventRepository eventRepository;
    CategoryRepository categoryRepository;
    EventMapper eventMapper;
    EntityManager entityManager;

    @Override
    @Transactional
    public EventResDto save(EventReqDto req) {
        Event event = eventMapper.toEvent(req);

        Category category = categoryRepository.findById(req.getCatgoryId())
                .orElseThrow(() -> new GlobalException(ErrorCode.CATEGORY_NOT_EXIST));

        if(category != null);
        event.setCategory(category);

        event = eventRepository.save(event);

        StringBuilder sql = new StringBuilder("INSERT INTO  regis_event(user_id, event_id, status) " +
                "values (:userId, :eventId, :status)");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("userId", req.getFaculty_id());
        query.setParameter("eventId", event.getId());
        query.setParameter("status", Status.PENDING);

        query.executeUpdate();//Luwu

        EventResDto res = eventMapper.toEventResDto(event);
        res.setFaculty_id(req.getFaculty_id());

        return res;
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
