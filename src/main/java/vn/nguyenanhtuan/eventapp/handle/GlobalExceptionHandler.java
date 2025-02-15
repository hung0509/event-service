package vn.nguyenanhtuan.eventapp.handle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.nguyenanhtuan.eventapp.constant.ErrorCode;
import vn.nguyenanhtuan.eventapp.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(exception = GlobalException.class)
    public ResponseEntity<ApiResponse<GlobalException>> handlerAppException(GlobalException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<GlobalException> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatus())
                .body(apiResponse);
    }
}
