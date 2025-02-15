package vn.nguyenanhtuan.eventapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventReqDto {
    String title;
    String description;
    Date startDate;
    Date endDate;
    int totalSeats;
    int availableSeats;
    Date createAt;
    String imageUrl;
    String bannerUrl;
    String location;
    String comment;
    String status;
    int facultyId;
}