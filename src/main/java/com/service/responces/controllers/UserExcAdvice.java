package com.service.responces.controllers;

import com.service.exceptions.UserIsException;
import com.service.exceptions.UserIsNotException;
import com.service.responces.Response;
import com.service.responces.annotations.UserExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = UserExceptionHandler.class)
public class UserExcAdvice {

    @ExceptionHandler(UserIsNotException.class)
    public ResponseEntity<Response> userIsNotException () {
        return new ResponseEntity<>(new Response("Пользователя не найден"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserIsException.class)
    private ResponseEntity<Response> userIsException () {
        return new ResponseEntity<>(new Response("Пользователь с таким именем уже существует"), HttpStatus.CONFLICT);
    }
}
