package vn.nguyenanhtuan.eventapp.dto.request;

import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateReq {
    String email;
    String password;
    String username;
    String phone;
    String facultyName;
    String facultyLogo;
    String facultyDescription;
    int role;
}
