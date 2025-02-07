package com.sawwere.sber.homework18.terminal.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение для оповещения о попытке доступа к заблокированному аккаунту.
 */
@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccountIsLockedException extends RuntimeException {
    private final long timeout;

    public AccountIsLockedException(String message, long timeout) {
        super(message);
        this.timeout = timeout;
    }

}
