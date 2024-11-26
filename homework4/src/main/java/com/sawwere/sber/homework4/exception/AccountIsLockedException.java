package com.sawwere.sber.homework4.exception;

/**
 * Исключение для оповещения о попытке доступа к заблокированному аккаунту.
 */
public class AccountIsLockedException extends RuntimeException {
    private final long timeout;

    public AccountIsLockedException(String message, long timeout) {
        super(message);
        this.timeout = timeout;
    }

    public long getTimeout() {
        return timeout;
    }
}
