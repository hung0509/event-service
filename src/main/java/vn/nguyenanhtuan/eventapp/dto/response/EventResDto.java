package vn.nguyenanhtuan.eventapp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.nguyenanhtuan.eventapp.entity.Category;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventResDto {
    int id;
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
    String categoryId;
    String categoryName;
    int faculty_id;
}