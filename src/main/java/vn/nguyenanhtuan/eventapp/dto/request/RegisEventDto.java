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
    Integer user_id;
    Integer event_id;
    String status;
    String guestName;
    String emailGuest;
    String phoneGuest;
}
