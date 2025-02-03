package com.qltv.QLTV.Exception;

import com.qltv.QLTV.DTO.Response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExeption {
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<Void>> handleException(RuntimeException ex){
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(ErrorCode.UNCATEGORIZED.getCode())
                .message(ErrorCode.UNCATEGORIZED.getMessage())
                .build();
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = ApplicationException.class)
    ResponseEntity<ApiResponse<Void>> handleApplicationException(ApplicationException ex){
        ErrorCode errorCode = ex.getErrorCode();
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = org.springframework.security.access.AccessDeniedException.class)
    ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String mess = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.KEY_INVALID;
        Map<String, Object> attributes = null;

        try{
            errorCode = ErrorCode.valueOf(mess);
            var constrainViolation = ex.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
            attributes = constrainViolation.getConstraintDescriptor().getAttributes();

        } catch (IllegalArgumentException e){
            log.error("KEY_INVALID");
        }
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes) ?
                        mapAttribute(errorCode.getMessage(), attributes) :
                        errorCode.getMessage()
        );
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes){
        String minvalue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = attributes.containsKey(MIN_ATTRIBUTE) ? String.valueOf(attributes.get(MAX_ATTRIBUTE)) : null;

        message = message.replace("{" + MIN_ATTRIBUTE + "}", minvalue);
        if(maxValue != null){
            message = message.replace("{" + MAX_ATTRIBUTE + "}", maxValue);
        }
        return message;
    }
}
