package com.sawwere.sber.homework12.exception;


public class SerializationException extends CacheException {
    public SerializationException(String message, Throwable e) {
        super(message, e);
    }
}