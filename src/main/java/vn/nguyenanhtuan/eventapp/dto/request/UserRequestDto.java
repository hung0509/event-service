package vn.nguyenanhtuan.eventapp.dto.request;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
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

    @Nullable
    String username;

    @Nullable
    String phone;

    @Nullable
    String facultyName;

    @Nullable
    String facultyLogo;

    @Nullable
    String facultyDescription;

    int role;
}
