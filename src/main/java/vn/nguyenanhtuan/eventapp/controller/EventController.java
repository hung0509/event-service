package vn.nguyenanhtuan.eventapp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.nguyenanhtuan.eventapp.dto.ApiResponse;
import vn.nguyenanhtuan.eventapp.dto.request.EventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;
import vn.nguyenanhtuan.eventapp.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class EventController {
    EventService eventService;

    @GetMapping
    public ApiResponse<List<EventResDto>> getAllEvent(){
        log.info("*Log table event. get all event  in db*");
        return ApiResponse.<List<EventResDto>>builder()
                .result(eventService.getAll())
                .message("get all event Successfully!")
                .build();
    }

    @GetMapping("/{status}")
    public ApiResponse<List<EventResDto>> getEvent(@PathVariable String status) {
        log.info("*Log table event. get event by staus in db*");
        return ApiResponse.<List<EventResDto>>builder()
                .result(eventService.getEventByStatus(status))
                .message("get event by status Successfully!")
                .build();
    }

    @GetMapping("/id/{id}")
    public ApiResponse<EventResDto> getId(@PathVariable int id) {
        log.info("*Log table event. get event by id in db*");
        return ApiResponse.<EventResDto>builder()
                .result(eventService.getById(id))
                .message("get event by id Successfully!")
                .build();
    }

    @PostMapping
    public ApiResponse<EventResDto> addEvent(@RequestBody EventReqDto req) {
        log.info("*Log table event. save event in db*");
        return ApiResponse.<EventResDto>builder()
                .result(eventService.save(req))
                .message("Save event Successfully!")
                .build();
    }
}
