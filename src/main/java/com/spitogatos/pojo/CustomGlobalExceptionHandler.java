package com.spitogatos.pojo;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex) {

        CustomErrorResponse errors = new CustomErrorResponse();
//        errors.setTimestamp(LocalDateTime.now());
//        errors.setErrorMsg(error!=null ? error.getErrorMsg(): "something went wrong");
//        errors.setStatus(error != null ? error.getErrorCode() : 666);

        errors.setTimestamp(LocalDateTime.now());
        errors.setErrorMsg(ex.getMessage());
        errors.setStatus(1000);

        return new ResponseEntity<>(errors, HttpStatus.I_AM_A_TEAPOT);

    }
}
