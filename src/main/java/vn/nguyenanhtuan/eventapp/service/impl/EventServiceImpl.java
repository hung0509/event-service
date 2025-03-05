package vn.nguyenanhtuan.eventapp.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.nguyenanhtuan.eventapp.constant.ErrorCode;
import vn.nguyenanhtuan.eventapp.constant.Status;
import vn.nguyenanhtuan.eventapp.dto.request.EventReqDto;
import vn.nguyenanhtuan.eventapp.dto.request.RegisEventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;
import vn.nguyenanhtuan.eventapp.entity.Category;
import vn.nguyenanhtuan.eventapp.entity.Event;
import vn.nguyenanhtuan.eventapp.handle.GlobalException;
import vn.nguyenanhtuan.eventapp.mapper.EventMapper;
import vn.nguyenanhtuan.eventapp.reposiroty.CategoryRepository;
import vn.nguyenanhtuan.eventapp.reposiroty.EventRepository;
import vn.nguyenanhtuan.eventapp.service.EventService;
import vn.nguyenanhtuan.eventapp.specification.EventSpecification;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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
    AmazonS3 amazonS3;

    @NonFinal
    @Value("${aws.bucket}")
    String AWS_BUCKET;

    @NonFinal
    @Value("${aws.folder}")
    String AWS_FOLDER;

    @Override
    @Transactional
    public EventResDto save(EventReqDto req) throws IOException, GeneralSecurityException {
        Event event = eventMapper.toEvent(req);

        event.setStatus(Status.PENDING.name());

        Category category = categoryRepository.findById(req.getCatgoryId())
                .orElseThrow(() -> new GlobalException(ErrorCode.CATEGORY_NOT_EXIST));

        if(category != null);
        event.setCategory(category);

        if(req.getBannerUrl() != null)
            event.setBannerUrl(uploadImage(req.getBannerUrl()));
        if(req.getImageUrl() != null)
        event.setImageUrl(uploadImage(req.getImageUrl()));

        try {
            event.setStartDate(convertStringtoDate(req.getStartDate()));
            event.setStartDate(convertStringtoDate(req.getEndDate()));
        }catch (Exception e){
            throw new GlobalException(ErrorCode.NOT_VALID_DATE);
        }
        event = eventRepository.save(event);

        StringBuilder sql = new StringBuilder("INSERT INTO  regis_event(user_id, event_id, status) " +
                "values (:userId, :eventId, :status)");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("userId", req.getFaculty_id());
        query.setParameter("eventId", event.getId());
        query.setParameter("status", null);

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
    public List<EventResDto> getAll(RegisEventReqDto req){
        Pageable page = PageRequest.of(req.getPage(), req.getPageSize());
        Specification<Event> spec = EventSpecification.getSpecification(req);
        var list = eventRepository.findAll(spec, page);
        return list.stream().map(eventMapper::toEventResDto).toList();
    }

    @Override
    @Transactional
    public EventResDto update(String status, int id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.EVENT_NOT_EXIST));
        log.info(status);
        if(status.equals(Status.APPROVE.name())){
            event.setStatus(status);
        }else if(status.equals(Status.REJECT.name())){
            event.setStatus(status);
        }else{
            throw new GlobalException(ErrorCode.STATUS_NOT_EXIST);
        }

        event = eventRepository.saveAndFlush(event);
        return eventMapper.toEventResDto(event);
    }

    private String uploadImage(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        InputStream inputStream = file.getInputStream();

        //Kiểm tra nếu không phải là ảnh thì không cho phép tiếp tục
        if (!contentType.equals("image/jpeg")
                && !contentType.equals("image/png")
                && !contentType.equals("image/webp")
                && !contentType.equals("image/gif")
                && !contentType.equals("image/bmp")) {
            throw new GlobalException(ErrorCode.NOT_VALID_FORMAT_IMAGE);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);

        String keyName = AWS_FOLDER + "/" + file.getOriginalFilename();
        PutObjectRequest request = new PutObjectRequest(AWS_BUCKET, keyName, inputStream, metadata);
        amazonS3.putObject(request);

        URL url = amazonS3.getUrl(AWS_BUCKET, keyName);
        return url.toString();
    }


    private Date convertStringtoDate(String dateInit){
        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Phân tích cú pháp chuỗi ban đầu thành đối tượng Date
            Date date = outputFormatter.parse(dateInit);
            // Định dạng lại đối tượng Date thành chuỗi có dạng yyyy/MM/dd

            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
