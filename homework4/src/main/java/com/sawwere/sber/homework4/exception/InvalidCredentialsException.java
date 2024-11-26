package com.sawwere.sber.homework4.exception;

/**
 * Исключение для оповещения о попытке аутентификации с неправильными данными.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
