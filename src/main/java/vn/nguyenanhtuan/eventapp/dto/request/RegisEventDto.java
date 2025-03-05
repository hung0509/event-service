package vn.nguyenanhtuan.eventapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisEventDto {
    int id;
    int user_id;
    int event_id;
    String status;
}
