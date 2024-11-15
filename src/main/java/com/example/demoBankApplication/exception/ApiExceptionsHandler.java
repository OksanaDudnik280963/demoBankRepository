package com.example.demoBankApplication.exception;

import com.example.demoBankApplication.exception.ErrorModelDTO.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionsHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(code = HttpStatus.BAD_REQUEST)
        @ResponseBody
        public ErrorModelDTO handleValidationError(MethodArgumentNotValidException ex) {
            BindingResult bindingResult = ex.getBindingResult();
            Set<String> fields = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)).keySet();
            FieldError fieldError = bindingResult.getFieldError();
            String defaultMessage = fieldError.getDefaultMessage();
            return new ErrorModelDTO(
                    ErrorDTO.builder()
                    .code(HttpStatus.BAD_REQUEST.name())
                    .fields((String[])fields.toArray())
                    .message(defaultMessage).build()
            );
        }
}
