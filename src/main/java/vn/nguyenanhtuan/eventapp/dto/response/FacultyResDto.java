package vn.nguyenanhtuan.eventapp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.nguyenanhtuan.eventapp.entity.Event;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FacultyResDto {
    int id;
    String facultyName;
    String facultyLogo;
    List<Event> events;
}
