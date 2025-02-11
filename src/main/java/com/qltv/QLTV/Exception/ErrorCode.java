package com.qltv.QLTV.Exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED(1006, "Uncategorized", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(1005, "Not found", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "User not found", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND(1005, "Book not found", HttpStatus.BAD_REQUEST),
    ARTICLE_NOT_FOUND(1005, "Article not found", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND(1005, "Comment not found", HttpStatus.BAD_REQUEST),
    BORROW_BOOK_NOT_FOUND(1005, "Borrow book not found", HttpStatus.BAD_REQUEST),
    NOT_EMPTY(1004, "This field is not empty", HttpStatus.BAD_REQUEST),
    NOT_NULL(1004, "This field is not null", HttpStatus.BAD_REQUEST),
    NAME_EXISTS(1001, "Name is already exist", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_EXISTS(1007, "Username not exist", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1002, "Username must be in {min} - {max} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1002, "Password must be in {min} - {max} characters", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1002, "Password must be {min} or {max} characters", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1002, "This field must be email", HttpStatus.BAD_REQUEST),
    KEY_INVALID(1003, "Key invalid", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1008, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You don't have permission", HttpStatus.FORBIDDEN),
    SIGN_IN_FAIL(1009, "Log-in fail", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1004, "Age must be at least {min}", HttpStatus.BAD_REQUEST),
    GENRE_NOT_FOUND(1004, "Genre is not found", HttpStatus.BAD_REQUEST),
    ;
    final int code;
    final String message;
    final HttpStatus httpStatus;
}
