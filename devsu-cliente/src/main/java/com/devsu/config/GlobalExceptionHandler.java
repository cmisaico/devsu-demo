package com.devsu.config;

import com.devsu.exceptions.ClienteExisteException;
import com.devsu.exceptions.ClienteNoExisteException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClienteNoExisteException.class)
    protected ResponseEntity<Object> handleClienteNoExisteException(ClienteNoExisteException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClienteExisteException.class)
    protected ResponseEntity<Object> handleClienteExisteException(ClienteExisteException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Object> handleException(WebExchangeBindException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationErrors(ConstraintViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("mensaje", "Por favor intente ingresar mas tarde");
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorMap = new HashMap<>();
        errorMap.put("mensaje", errors);
        return errorMap;
    }

}
