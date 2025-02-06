package com.sawwere.sber.homework18.terminal.service;

import com.sawwere.sber.homework18.terminal.exception.IllegalAmountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TerminalService {

    private int currentAmount = 0;

    /**
     * Возвращает количество средств на счету.
     *
     * @return количество средств на счету
     */
    public Integer getBalance() {
        return currentAmount;
    }

    /**
     * Пополняет счет на заданное количество средств.
     *
     * @param amount количество денег, которое будет прибавлено к счету
     */
    public void deposit(int amount) {
        verifyAmount(amount);
        currentAmount += amount;
    }

    /**
     * Снимает со счета заданное количество средств.
     *
     * @param amount количество денег, которое будет вычтено со счета
     */
    public void withdraw(int amount) {
        verifyAmount(amount);
        if (currentAmount - amount < 0) {
            throw new IllegalAmountException(
                    "Сумма превышает текущий баланс средств."
            );
        }
        currentAmount -= amount;
    }

    /**
     * Проверяет, корректная ли сумма подана в качестве параметра операции.
     * Сервер удостоверяется в правильности переданных ему данных
     * на случай обхода или отсутствия таких проверок со стороны клиента.
     *
     * @param amount сумма, которую нужно проверить
     * @throws IllegalAmountException в случае, когда сумма неположительная или не кратна 100
     */
    private void verifyAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalAmountException(
                    "Сумма должна быть неотрицательным числом."
            );
        } else if (amount % 100 != 0) {
            throw new IllegalAmountException(
                    "Сумма должна быть кратна 100."
            );
        }
    }
}
