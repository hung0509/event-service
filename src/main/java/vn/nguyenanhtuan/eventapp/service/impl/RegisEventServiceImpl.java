package vn.nguyenanhtuan.eventapp.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;
import vn.nguyenanhtuan.eventapp.constant.ErrorCode;
import vn.nguyenanhtuan.eventapp.constant.ParseHelper;
import vn.nguyenanhtuan.eventapp.constant.Status;
import vn.nguyenanhtuan.eventapp.dto.request.RegisEventDto;
import vn.nguyenanhtuan.eventapp.dto.request.RegisEventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;
import vn.nguyenanhtuan.eventapp.entity.Event;
import vn.nguyenanhtuan.eventapp.entity.Guest;
import vn.nguyenanhtuan.eventapp.entity.User;
import vn.nguyenanhtuan.eventapp.handle.GlobalException;
import vn.nguyenanhtuan.eventapp.mapper.EventMapper;
import vn.nguyenanhtuan.eventapp.mapper.UserMapper;
import vn.nguyenanhtuan.eventapp.reposiroty.EventRepository;
import vn.nguyenanhtuan.eventapp.reposiroty.GuestRepository;
import vn.nguyenanhtuan.eventapp.reposiroty.RegisEventRepository;
import vn.nguyenanhtuan.eventapp.reposiroty.UserRepository;
import vn.nguyenanhtuan.eventapp.service.RigisEventService;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisEventServiceImpl implements RigisEventService{
    RegisEventRepository regisEventRepository;
    DataSource dataSource;
    EntityManager entityManager;
    EventMapper eventMapper;
    UserMapper userMapper;
    EventRepository eventRepository;
    UserRepository userRepository;
    GuestRepository guestRepository;

    @Override
    public List<EventResDto> getEventHaveFilter(RegisEventReqDto req) {
        StringBuilder sql =  new StringBuilder("select te.*  from regis_event tre " +
                " join user u on u.id = tre.user_id " +
                " join event te on tre.event_id = te.id where user_id = :userId and role_id = 2 ");

        if(req.getStatus() != null){
            sql.append(" and te.status = :status ");
        }

        if(req.getTitle() != null){
            sql.append(" and title like :title ");
        }

//        if(req.getTitle() != null){
//            sql.append(" and location like :location ");
//        }
        if(req.getLocation() != null){
            sql.append(" and location like :location ");
        }

        if(req.getStartDate() != null){
            sql.append(" and start_date >= :startDate ");
        }

        if(req.getEndDate() != null){
            sql.append(" and end_date <= :endDate ");
        }

        log.info(req.getCategoryId() + "");
        if(req.getCategoryId() != null){
            sql.append(" and category_id = :categoryId ");
        }

        Query query =  entityManager.createNativeQuery(sql.toString());
        query.setParameter("userId", req.getFaculty_id());

        if(req.getCategoryId() != null) {
            query.setParameter("categoryId", req.getCategoryId());
        }

        if(req.getStartDate() != null) {
            query.setParameter("startDate", req.getStartDate());
        }
        if(req.getEndDate() != null) {
            query.setParameter("endDate", req.getEndDate());
        }
        if(req.getStatus() != null) {
            query.setParameter("status", req.getStatus());
        }
        if(req.getTitle() != null) {
            query.setParameter("title", "%" + req.getTitle() + "%");
        }
        if(req.getLocation() != null) {
            query.setParameter("location", "%" + req.getLocation() + "%");
        }

        List<Map<String, Object>> results = query.unwrap(NativeQuery.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .getResultList();

        List<Event> eventResDtos = new ArrayList<>();
        try{
            results.forEach(rs -> {
                log.info(rs.get("start_date"). toString());
                Event eventResDto = Event.builder()
                        .id(ParseHelper.INT.parse(rs.get("id")))
                        .totalSeats(ParseHelper.INT.parse(rs.get("total_seats")))
                        .title(ParseHelper.STRING.parse(rs.get("title")))
                        .description(ParseHelper.STRING.parse(rs.get("description")))
                        .location(ParseHelper.STRING.parse(rs.get("location")))
                        .startDate(convertStringtoDate(ParseHelper.INSTANT.parse(rs.get("start_date"))))
                        .endDate(convertStringtoDate(ParseHelper.INSTANT.parse(rs.get("end_date"))))
                        .bannerUrl(ParseHelper.STRING.parse(rs.get("banner_url")))
                        .imageUrl(ParseHelper.STRING.parse(rs.get("image_url")))
                        .status(ParseHelper.STRING.parse(rs.get("status")))
                        .availableSeats(ParseHelper.INT.parse(rs.get("available_seats")))
                        .build();
                eventResDtos.add(eventResDto);
            });

            return eventResDtos.stream().map(eventMapper::toEventResDto).toList();
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<EventResDto> getEventHaveFilterV2(RegisEventReqDto req) {
        StringBuilder sql =  new StringBuilder("select te.*, tre.status as 'status_event_of_user' from regis_event tre " +
                " join user u on u.id = tre.user_id " +
                " join event te on tre.event_id = te.id where user_id = :userId and role_id = 3 ");

        if(req.getStatus() != null){
            sql.append(" and tre.status like :status ");
        }

        if(req.getTitle() != null){
            sql.append(" and title like :title ");
        }

//        if(req.getTitle() != null){
//            sql.append(" and location like :location ");
//        }
        if(req.getLocation() != null){
            sql.append(" and location like :location ");
        }

        if(req.getStartDate() != null){
            sql.append(" and start_date >= :startDate ");
        }

        if(req.getEndDate() != null){
            sql.append(" and end_date <= :endDate ");
        }

        if(req.getCategoryId() != null){
            sql.append(" and category_id = :categoryId ");
        }

        Query query =  entityManager.createNativeQuery(sql.toString());
        query.setParameter("userId", req.getFaculty_id());

        if(req.getCategoryId() != null) {
            query.setParameter("categoryId", req.getCategoryId());
        }

        if(req.getStartDate() != null) {
            query.setParameter("startDate", req.getStartDate());
        }
        if(req.getEndDate() != null) {
            query.setParameter("endDate", req.getEndDate());
        }
        if(req.getStatus() != null) {
            query.setParameter("status", req.getStatus());
        }
        if(req.getTitle() != null) {
            query.setParameter("title", "%" + req.getTitle() + "%");
        }
        if(req.getLocation() != null) {
            query.setParameter("location", "%" + req.getLocation() + "%");
        }

        List<Map<String, Object>> results = query.unwrap(NativeQuery.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .getResultList();

        List<Event> eventResDtos = new ArrayList<>();
        try{
            results.forEach(rs -> {
                log.info(rs.get("start_date"). toString());
                Event eventResDto = Event.builder()
                        .id(ParseHelper.INT.parse(rs.get("id")))
                        .availableSeats(ParseHelper.INT.parse(rs.get("total_seats")))
                        .title(ParseHelper.STRING.parse(rs.get("title")))
                        .description(ParseHelper.STRING.parse(rs.get("description")))
                        .location(ParseHelper.STRING.parse(rs.get("location")))
                        .startDate(convertStringtoDate(ParseHelper.INSTANT.parse(rs.get("start_date"))))
                        .endDate(convertStringtoDate(ParseHelper.INSTANT.parse(rs.get("end_date"))))
                        .bannerUrl(ParseHelper.STRING.parse(rs.get("banner_url")))
                        .imageUrl(ParseHelper.STRING.parse(rs.get("image_url")))
                        .status(ParseHelper.STRING.parse(rs.get("status_event_of_user")))
                        .availableSeats(ParseHelper.INT.parse(rs.get("available_seats")))
                        .build();
                eventResDtos.add(eventResDto);
            });

            return eventResDtos.stream().map(eventMapper::toEventResDto).toList();
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    @Transactional
    public Integer registerEvent(RegisEventDto req) {
        Event event = eventRepository.findById(req.getEvent_id()).orElseThrow(()
                -> new GlobalException(ErrorCode.EVENT_NOT_EXIST));
        if(req.getUser_id() != null) {
            User user = userRepository.findById(req.getUser_id()).orElseThrow(() ->
                    new GlobalException(ErrorCode.USER_NOT_EXIST));

            StringBuilder sql = new StringBuilder("insert into regis_event(event_id, user_id, status) values(:event_id, :user_id, :status)");

            Query query = entityManager.createNativeQuery(sql.toString());
            query.setParameter("event_id", req.getEvent_id());
            query.setParameter("user_id", user.getId());
            query.setParameter("status", Status.REGISTERED.name());
            int i = query.executeUpdate();
            return i;
        }else{ // Tai khaon khach
            Guest guest = Guest.builder()
                    .email(req.getEmailGuest())
                    .phone(req.getPhoneGuest())
                    .username(req.getGuestName())
                    .build();

            guest = guestRepository.save(guest);
            StringBuilder sql = new StringBuilder("insert into regis_event(event_id, guest_id, status) values(:event_id, :guest_id, :status)");
            Query query = entityManager.createNativeQuery(sql.toString());
            query.setParameter("event_id", req.getEvent_id());
            query.setParameter("guest_id", guest.getId());
            query.setParameter("status", Status.REGISTERED.name());
            int i = query.executeUpdate();
            return i;
        }
    }

    @Override
    public List<UserResponseDto> getUserByEventId(int id) {
        StringBuilder sql = new StringBuilder("select * from d_list_user_regis where event_id = :id");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("id", id);

        List<Map<String, Object>> results = query.unwrap(NativeQuery.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .getResultList();
        List<UserResponseDto> data = new ArrayList<>();
        try{
            results.forEach((rs) -> {
                UserResponseDto u = UserResponseDto.builder()
                        .id(ParseHelper.INT.parse(rs.get("id")))
                        .username(ParseHelper.STRING.parse(rs.get("username")))
                        .email(ParseHelper.STRING.parse(rs.get("email")))
                        .build();
                data.add(u);
            });

            return data;
        }catch (Exception e){
            e.printStackTrace();
        }
        return List.of();
    }

    private Date convertStringtoDate(Instant dateInit){
        if(dateInit == null)
            return null;
        LocalDateTime localDateTime = dateInit.atZone(ZoneId.of("UTC")).toLocalDateTime();
        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Format the date to String
        String formattedDateStr = localDateTime.format(formatter);
        // Convert String to Date
        Date formattedDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            formattedDate = sdf.parse(formattedDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

}
