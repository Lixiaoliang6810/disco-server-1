package com.miner.disco.boot.support;


import com.miner.disco.basic.exception.BasicException;
import com.miner.disco.basic.model.response.ViewData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * Created by lubycoder@163.com on 2018/3/17.
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BasicException.class)
    public ViewData baseException(Exception exception) {
        BasicException be = (BasicException) exception;
        return ViewData.builder().status(be.getCode()).message(be.getMessage()).build();
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ViewData missingException(Exception exception) {
        return ViewData.builder().status(HttpStatus.BAD_REQUEST.value()).message(exception.getMessage()).build();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ViewData methodNotSupportedException(Exception exception) {
        return ViewData.builder().status(HttpStatus.METHOD_NOT_ALLOWED.value()).message(exception.getMessage()).build();
    }

    @ExceptionHandler(value = BindException.class)
    public ViewData bindException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return ViewData.builder().status(90000)
                .message(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()).build();
    }

    @ExceptionHandler(value = Throwable.class)
    public ViewData buzException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ViewData.builder().status(-2).message("系统错误").build();
    }

}
