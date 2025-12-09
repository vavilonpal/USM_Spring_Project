package org.moodle.springlaboratorywork.controllerAdvice;


import jakarta.validation.ConstraintViolationException;

import org.moodle.springlaboratorywork.logger.Logger;
import lombok.RequiredArgsConstructor;
import org.moodle.springlaboratorywork.logger.enums.LogLevel;
import org.moodle.springlaboratorywork.validation.ValidationErrorResponse;
import org.moodle.springlaboratorywork.validation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorHandlingControllerAdvice {
    private final Logger validationLogger;
    protected static Function<List<Violation>, String> fieldsConcatFunc = violationsList -> {
        StringBuilder concatString = new StringBuilder();
        for (Violation vio : violationsList) {
            concatString.append("Field: ").
                    append(vio.getFieldName())
                    .append(", ErrorMessage:")
                    .append(vio.getMessage())
                    .append("; ");
        }
        return concatString.toString();
    };

    /**
     * Метод для обработки ошибок валидации внутри тела запроса
     *
     * @param e - ConstraintViolationException - метод ловит это исключение и обрабаывает его получая из его тела все ошибки
     * @return ErrorResponse - который хранит в себе поле в котором была ошибка и сам текст ошибки
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());



        validationLogger.write(LogLevel.ERROR.toString(), fieldsConcatFunc.apply(violations), ConstraintViolationException.class);
        return new ValidationErrorResponse(violations);
    }


    /**
     * Метод ообрабатывает ошибки валидации в параметрах метода
     *
     * @param e MethodArgumentNotValidException - исключение из тела которого мы получаем ошибки валдации.
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        validationLogger.write(LogLevel.ERROR.toString(), fieldsConcatFunc.apply(violations), MethodArgumentNotValidException.class);

        return new ValidationErrorResponse(violations);
    }

}
