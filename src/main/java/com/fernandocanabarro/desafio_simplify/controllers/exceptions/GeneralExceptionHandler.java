package com.fernandocanabarro.desafio_simplify.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.fernandocanabarro.desafio_simplify.dtos.exceptions.StandardError;
import com.fernandocanabarro.desafio_simplify.dtos.exceptions.ValidationError;
import com.fernandocanabarro.desafio_simplify.services.exceptions.ResourceNotFoundException;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<StandardError>> notFound(ResourceNotFoundException e, ServerHttpRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), request.getPath().toString());
        return Mono.just(ResponseEntity.status(status).body(err));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ValidationError>> invalidData(WebExchangeBindException e, ServerHttpRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados Inválidos", request.getPath().toString());
        for (FieldError f : e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return Mono.just(ResponseEntity.status(status).body(err));
    }
}
