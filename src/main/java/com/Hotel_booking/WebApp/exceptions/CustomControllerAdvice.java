package com.Hotel_booking.WebApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.PrintWriter;
import java.io.StringWriter;

// @ControllerAdvice funciona empleando la anotación a nivel de método @ExceptionHandler.
// Esta anotación permite definir qué método debe llamarse en caso de error.
// Específicamente, la excepción lanzada se compara con las excepciones pasadas como parámetros a @ExceptionHandler
// según el tipo. Se llama al primer método donde hay una coincidencia.
// Si ninguno coincide, se prueba la clase principal de la excepción, y así sucesivamente.
// Por esta razon se debe implementar un método alternativo para cubrir todos los posibles casos restantes.



@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> handleCustomErrorExceptions(
            Exception e
    ) {
        // casting the generic Exception e to CustomErrorException
        CustomErrorException customErrorException = (CustomErrorException) e;

        HttpStatus status = customErrorException.getStatus();

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        customErrorException.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        customErrorException.getMessage()
//                        stackTrace,
//                        customErrorException.getData()
                ),
                status
        );
    }
}
