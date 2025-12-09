
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
    public ResponseEntity<Map<String, String>> manejarErrorFormato(HttpMessageNotReadableException ex) {

        Map<String, String> error = new HashMap<>();

        // Detectamos si realmente es un error de fecha
        if (ex.getMessage() != null && ex.getMessage().contains("DateTimeParseException")) {
            error.put("fecha", "Formato de fecha inválido. Debes usar yyyy-MM-dd'T'HH:mm");
        } else {
            error.put("error", "El cuerpo de la petición es inválido o está vacío");
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }







}