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
public class RegisEventReqDto {
    int faculty_id;
    String title;
    String startDate;
    String endDate;
    String location;
    String status;
    Integer categoryId;
    String key;
    Integer page = 0;
    Integer pageSize = 6;
}
