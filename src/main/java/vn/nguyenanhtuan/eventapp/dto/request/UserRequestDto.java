package vn.nguyenanhtuan.eventapp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    @NotNull
    @Email
    String email;
    String password;
    String username;
    String phone;
    int role;
}
