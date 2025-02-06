package com.sawwere.sber.homework18.terminal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение для оповещения о попытке выполнения операции с некорректным значением средств.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalAmountException extends IllegalArgumentException{
    public IllegalAmountException(String message) {
        super(message);
    }
}
