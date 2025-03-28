package vn.nguyenanhtuan.eventapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventReqDto{
    int faculty_id;
    String title;
    String description;
    String startDate;
    String endDate;
    int totalSeats;
    int availableSeats;
    MultipartFile bannerUrl;
    MultipartFile imageUrl;
    Date createAt;
    String location;
    String comment;
    String status;
    int catgoryId;

    String key;
}