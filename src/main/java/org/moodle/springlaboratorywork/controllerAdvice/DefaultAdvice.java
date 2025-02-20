package org.moodle.springlaboratorywork.controllerAdvice;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultAdvice {

    /*@ExceptionHandler()
    public ResponseEntity<ErrorResponse> coachEntityNotFound(){
        ErrorResponse errorResponse = new ErrorResponse("Coah");
    }*/
}
