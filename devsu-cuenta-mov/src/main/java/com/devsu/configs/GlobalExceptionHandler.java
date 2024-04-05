package com.devsu.configs;


import com.devsu.commons.ConstanteUtil;
import com.devsu.exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ConstanteUtil.MENSAJE, ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FechaNoValidaException.class)
    protected ResponseEntity<Object> handleFechaNoValidaException(FechaNoValidaException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ConstanteUtil.MENSAJE, ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClienteNoExisteException.class)
    protected ResponseEntity<Object> handleClienteNoExisteException(ClienteNoExisteException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ConstanteUtil.MENSAJE, ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServicioNoDisponible.class)
    protected ResponseEntity<Object> handleServicioNoDisponible(ServicioNoDisponible ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ConstanteUtil.MENSAJE, ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CuentaNoExisteException.class)
    protected ResponseEntity<Object> handleCuentaNoExisteException(CuentaNoExisteException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ConstanteUtil.MENSAJE, ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SaldoNoDisponibleException.class)
    protected ResponseEntity<Object> handleSaldoNoDisponibleException(SaldoNoDisponibleException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ConstanteUtil.MENSAJE, ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SaldoInsuficienteException.class)
    protected ResponseEntity<Object> handleSaldoInsuficienteException(SaldoInsuficienteException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ConstanteUtil.MENSAJE, ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Object> handleException(WebExchangeBindException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream().map(ConstraintViolation::getMessage).toList();

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorMap = new HashMap<>();
        errorMap.put(ConstanteUtil.MENSAJE, errors);
        return errorMap;
    }

}
