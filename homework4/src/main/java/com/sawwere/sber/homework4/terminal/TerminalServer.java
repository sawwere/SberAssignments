package com.sawwere.sber.homework4.terminal;

public interface TerminalServer {
    int ERROR_LIMIT = 3;
    long LOCK_TIMEOUT = 10L;

    /**
     * Возвращает количество средств на счету.
     *
     * @return количество средств на счету
     */
    Integer check();

    /**
     * Пополняет счет на заданное количество средств.
     *
     * @param amount количество денег, которое будет прибавлено к счету
     */
    void deposit(int amount);

    /**
     * Снимает со счета заданное количество средств.
     *
     * @param amount количество денег, которое будет вычтено со счета
     */
    void withdraw(int amount);

    /**
     * Выполняет аутентификацию клиента по его пин-коду.
     *
     * @param pinCode проверяемый пин-код
     */

    void login(String pinCode);
}
