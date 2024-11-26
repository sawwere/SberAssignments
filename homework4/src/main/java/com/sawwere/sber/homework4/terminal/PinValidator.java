package com.sawwere.sber.homework4.terminal;

public class PinValidator {

    public boolean isValidSymbol(char c) {
        return Character.isDigit(c);
    }

    public boolean validate(String pinCode) {
        return pinCode.matches("^\\d{4}$");
    }
}
