package com.dataartschool.newsportal.controller;

import com.dataartschool.newsportal.controller.dto.ErrorDto;
import com.dataartschool.newsportal.exception.*;
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

    @ExceptionHandler(NoInnerTextInArticleFileFound.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorDto handleNoInnerTextInArticleFileFound(NoInnerTextInArticleFileFound e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(NotAcceptableFileReceived.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorDto handleNotAcceptableFileReceived(NotAcceptableFileReceived e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(NoTitleInArticleFileFound.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorDto handleNoTitleInArticleFileFound(NoTitleInArticleFileFound e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(UnexpectedFileInZip.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorDto handleUnexpectedFileInZip(UnexpectedFileInZip e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(ZipIsEmpty.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ErrorDto handleZipIsEmpty(ZipIsEmpty e) {
        return new ErrorDto(e.getMessage());
    }

}