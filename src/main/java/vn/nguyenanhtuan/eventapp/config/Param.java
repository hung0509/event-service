package vn.nguyenanhtuan.eventapp.config;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Param {
    public static final String STATUS_REGISTER = "REGISTER";
    public static final String STATUS_ATTENDED = "ATTENDED";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVE = "APPROVE";
    public static final String STATUS_REJECT = "REJECT";

}
