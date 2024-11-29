package com.sawwere.sber.homework4.terminal;

import com.sawwere.sber.homework4.exception.AccountIsLockedException;
import com.sawwere.sber.homework4.exception.IllegalAmountException;
import com.sawwere.sber.homework4.exception.InvalidCredentialsException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TerminalServerImpl implements TerminalServer {
    private int currentAmount = 0;
    private int loginErrorCounter = 0;
    private boolean isBlocked = false;
    private Instant blockTime;

    private final String correctPinCode;

    public TerminalServerImpl(String correctPinCode) {
        this.correctPinCode = correctPinCode;
    }


    @Override
    public Integer check() {
        return currentAmount;
    }

    @Override
    public void deposit(int amount) {
        verifyAmount(amount);
        currentAmount += amount;
    }

    @Override
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

    @Override
    public void login(String pinCode) {
        if (isBlocked) {
            long timeout = Instant.now().minus(blockTime.getEpochSecond(), ChronoUnit.SECONDS).getEpochSecond();
            if (timeout < LOCK_TIMEOUT) {
                throw new AccountIsLockedException(
                        "Аккаунт заблокирован, попытка входа отклонена.",
                        LOCK_TIMEOUT - timeout
                );
            } else {
                loginErrorCounter = 0;
                isBlocked = false;
                blockTime = null;
            }
        }
        if (!pinCode.equals(correctPinCode)) {
            loginErrorCounter += 1;
            if (loginErrorCounter == ERROR_LIMIT) {
                loginErrorCounter = 0;
                isBlocked = true;
                blockTime = Instant.now();
                throw new AccountIsLockedException("Превышен лимит попыток входа, аккаунт заблокирован.", LOCK_TIMEOUT);
            }
            throw new InvalidCredentialsException("Неправильный пин-код.");
        }
    }
}
