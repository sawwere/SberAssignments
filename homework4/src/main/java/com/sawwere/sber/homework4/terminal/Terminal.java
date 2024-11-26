package com.sawwere.sber.homework4.terminal;

public interface Terminal {
    /**
     * Возвращает количество средств на счету
     */
    void check();

    /**
     * Пополняет счет на заданное количество средств
     */
    void deposit();

    /**
     * Снимает со счета заданное количество средств
     */
    void withdraw();

    void run();
}
