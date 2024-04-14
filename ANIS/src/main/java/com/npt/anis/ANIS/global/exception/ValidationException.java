package com.npt.anis.ANIS.global.exception;

/**
 * null 값이 전달될 때 발생하는 예외
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
