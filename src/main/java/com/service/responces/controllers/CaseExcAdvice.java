package com.service.responces.controllers;

import com.service.exceptions.CaseIsNotException;
import com.service.exceptions.MethodInNotAllowedException;
import com.service.exceptions.NullDateException;
import com.service.responces.Response;
import com.service.responces.annotations.CaseExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = CaseExceptionHandler.class)
public class CaseExcAdvice {

    @ExceptionHandler(CaseIsNotException.class)
    public ResponseEntity<Response> caseIsNotException () {
        return new ResponseEntity<>(new Response("Дело с таким id не существует"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Response> idFormatException () {
        return new ResponseEntity<>(new Response("Неверный формат ID"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodInNotAllowedException.class)
    public ResponseEntity<Response> methodInNotAllowedException () {
        return new ResponseEntity<>(new Response("Метод запрещён"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NullDateException.class)
    public ResponseEntity<Response> nullDateException () {
        return new ResponseEntity<>(new Response("Переданы пустые данные"), HttpStatus.BAD_REQUEST);
    }

}
