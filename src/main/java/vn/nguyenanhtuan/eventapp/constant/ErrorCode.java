package vn.nguyenanhtuan.eventapp.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHENTICATED(9999, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    USER_NOT_EXIST(1001, "User not exist", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXIST(1002, "Role not exist", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXIST(1003, "User is already exist", HttpStatus.BAD_REQUEST),
    FACULTY_NOT_EXIST(1004, "Faculty not exist", HttpStatus.BAD_REQUEST),
    EVENT_NOT_EXIST(1005, "Event not exist", HttpStatus.BAD_REQUEST),
    STATUS_NOT_EXIST(1006, "Status not exist", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXIST(1007, "category not exist", HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatus status;
}
