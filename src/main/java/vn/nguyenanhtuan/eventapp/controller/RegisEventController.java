package vn.nguyenanhtuan.eventapp.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.nguyenanhtuan.eventapp.dto.ApiResponse;
import vn.nguyenanhtuan.eventapp.dto.request.RegisEventDto;
import vn.nguyenanhtuan.eventapp.dto.request.RegisEventReqDto;
import vn.nguyenanhtuan.eventapp.dto.response.EventResDto;
import vn.nguyenanhtuan.eventapp.dto.response.UserResponseDto;
import vn.nguyenanhtuan.eventapp.service.RigisEventService;

import java.util.List;

@RestController
@RequestMapping("/event-user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class RegisEventController {
    RigisEventService rigisEventService;

    @GetMapping
    public ApiResponse<List<EventResDto>> getEventHaveFilter(@ModelAttribute RegisEventReqDto req){
        log.info("*Log table event-regis. get all event assign by user_id  in db*");
        return ApiResponse.<List<EventResDto>>builder()
                .result(rigisEventService.getEventHaveFilter(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<List<UserResponseDto>> getUserByEventId(@PathVariable int id){
        log.info("*Log table event-regis. get all event assign by user_id  in db*");
        return ApiResponse.<List<UserResponseDto>>builder()
                .result(rigisEventService.getUserByEventId(id))
                .build();
    }

    @PostMapping
    public ApiResponse<Integer> registerEvent(@RequestBody RegisEventDto req){
        log.info("*Log table event-regis. get all event assign by user_id  in db*");
        return ApiResponse.<Integer>builder()
                .result(rigisEventService.registerEvent(req))
                .build();
    }
}
