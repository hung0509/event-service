package vn.nguyenanhtuan.eventapp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashBoardDto {
    int countEvent;
    int countFaculty;
    int countAttention;
}
