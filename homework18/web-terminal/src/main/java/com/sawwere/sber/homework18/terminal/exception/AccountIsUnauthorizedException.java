package com.sawwere.sber.homework18.terminal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение для оповещения о попытке доступа к не авторизированному ресурсу.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccountIsUnauthorizedException extends RuntimeException {
    public AccountIsUnauthorizedException(String message) {
        super(message);
    }
}
