package com.sawwere.sber.homework18.crud.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends NoSuchElementException {
    /**
     * Constructs a {@code NoSuchElementException} with {@code null}
     * as its error message string.
     */
    public NotFoundException() {
    }

    /**
     * Constructs a {@code NoSuchElementException} with the specified detail
     * message and cause.
     *
     * @param s     the detail message, or null
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method), or null
     * @since 15
     */
    public NotFoundException(String s, Throwable cause) {
        super(s, cause);
    }

    /**
     * Constructs a {@code NoSuchElementException} with the specified cause.
     * The detail message is set to {@code (cause == null ? null :
     * cause.toString())} (which typically contains the class and
     * detail message of {@code cause}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method)
     * @since 15
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a {@code NoSuchElementException}, saving a reference
     * to the error message string {@code s} for later retrieval by the
     * {@code getMessage} method.
     *
     * @param s the detail message.
     */
    public NotFoundException(String s) {
        super(s);
    }
}
