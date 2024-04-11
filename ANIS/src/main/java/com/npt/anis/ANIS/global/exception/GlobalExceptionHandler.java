package com.npt.anis.ANIS.global.exception;

import com.npt.anis.ANIS.global.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * ResourceNotFoundException을 처리하는 메서드
     *
     * @param ex 발생한 ResourceNotFoundException
     * @return HTTP 상태 코드가 NOT_FOUND(404)이고, ErrorResponseDto를 포함한 ResponseEntity 반환
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found exception: {}", ex.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * ValidationException을 처리하는 메서드
     *
     * @param ex 발생한 ValidationException
     * @return HTTP 상태 코드가 BAD_REQUEST(400)이고, ErrorResponseDto를 포함한 ResponseEntity 반환
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(ValidationException ex) {
        log.error("ValidationException occurred: {}", ex.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * handleMethodArgumentNotValidException을 처리하는 메서드
     *
     * @param ex 발생한 handleMethodArgumentNotValidException
     * @return HTTP 상태 코드가 BAD_REQUEST(400)이고, ErrorResponseDto를 포함한 ResponseEntity 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException occurred: {}", ex.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
