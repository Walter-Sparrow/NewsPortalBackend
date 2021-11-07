package com.dataartschool.newsportal.controller;

import com.dataartschool.newsportal.controller.dto.ErrorDto;
import com.dataartschool.newsportal.exception.NoNewsSectionFound;
import com.dataartschool.newsportal.exception.NoNewsFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleRuntimeException(RuntimeException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(NoNewsFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleNotFoundException(NoNewsFound e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(NoNewsSectionFound.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleUserAlreadyExistsException(NoNewsSectionFound e) {
        return new ErrorDto(e.getMessage());
    }

}