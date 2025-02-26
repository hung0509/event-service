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
public class CategoryResDto {
    int id;
    String categoryName;
    String categoryLogo;
}
