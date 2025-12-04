
package com.aplicacion.eventos_escolares.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {


    //EXCEPCION SI EL ARGUMENTO NO ES VALIDO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> controladorErrores(MethodArgumentNotValidException exception){

        Map<String, String> mapaErrores = new HashMap<>();
        for(FieldError error : exception.getBindingResult().getFieldErrors()){
            mapaErrores.put(error.getField(), error.getDefaultMessage());}
        return new ResponseEntity<>(mapaErrores, HttpStatus.BAD_REQUEST);

    }

    //EXCEPCION SI NO ENCUENTRA ELEMENTO
    @ExceptionHandler(ElementoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarElementoNoEncontrado(ElementoNoEncontradoException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    //EXCEPCION FECHA
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarErrorFormatoFecha(HttpMessageNotReadableException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("fecha", "Formato de fecha inválido. Debes usar yyyy-MM-dd'T'HH:mm");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }




}