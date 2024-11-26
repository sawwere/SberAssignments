package com.sawwere.sber.homework4.exception;

/**
 * Исключение для оповещения о попытке выполнения операции с некорректным значением средств.
 */
public class IllegalAmountException extends IllegalArgumentException{
    public IllegalAmountException(String message) {
        super(message);
    }
}
