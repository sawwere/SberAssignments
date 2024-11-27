package com.sawwere.sber.homework4;

import com.sawwere.sber.homework4.exception.AccountIsLockedException;
import com.sawwere.sber.homework4.exception.IllegalAmountException;
import com.sawwere.sber.homework4.exception.InvalidCredentialsException;
import com.sawwere.sber.homework4.terminal.TerminalServer;
import com.sawwere.sber.homework4.terminal.TerminalServerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TerminalServerTest {
    private static final String CORRECT_PIN = "2214";
    private static final String INCORRECT_PIN = "0000";

    @Test
    void loginWithoutError() {
        TerminalServer underTest = new TerminalServerImpl(CORRECT_PIN);
        underTest.login(CORRECT_PIN);
    }

    @Test
    void loginWithIncorrectPin() {
        TerminalServer underTest = new TerminalServerImpl(CORRECT_PIN);
        Assertions.assertThrows(InvalidCredentialsException.class, () -> underTest.login(INCORRECT_PIN));
    }

    @Test
    void loginBlock() {
        TerminalServer underTest = new TerminalServerImpl(CORRECT_PIN);
        for (int i = 0; i < TerminalServer.ERROR_LIMIT - 1; i++) {
            Assertions.assertThrows(InvalidCredentialsException.class, () -> underTest.login(INCORRECT_PIN));
        }
        Assertions.assertThrows(AccountIsLockedException.class, () -> underTest.login(INCORRECT_PIN));
    }

    @Test
    void loginAfterBlock() {
        TerminalServer underTest = new TerminalServerImpl(CORRECT_PIN);
        for (int i = 0; i < TerminalServer.ERROR_LIMIT - 1; i++) {
            Assertions.assertThrows(InvalidCredentialsException.class, () -> underTest.login(INCORRECT_PIN));
        }
        Assertions.assertThrows(AccountIsLockedException.class, () -> underTest.login(INCORRECT_PIN));
        Assertions.assertThrows(AccountIsLockedException.class, () -> underTest.login(CORRECT_PIN));
    }

    @Test
    void loginAfterBlockTimeout() {
        TerminalServer underTest = new TerminalServerImpl(CORRECT_PIN);
        for (int i = 0; i < TerminalServer.ERROR_LIMIT - 1; i++) {
            Assertions.assertThrows(InvalidCredentialsException.class, () -> underTest.login(INCORRECT_PIN));
        }
        Assertions.assertThrows(AccountIsLockedException.class, () -> underTest.login(INCORRECT_PIN));
        try {
            Thread.sleep(1000 * TerminalServer.LOCK_TIMEOUT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        underTest.login(CORRECT_PIN);
    }

    @Test
    void deposit() {
        TerminalServer underTest = new TerminalServerImpl(CORRECT_PIN);
        underTest.deposit(1000);
        underTest.deposit(200);

        Assertions.assertEquals(1200, underTest.check());
    }

    @Test
    void withdraw() {
        TerminalServer underTest = new TerminalServerImpl(CORRECT_PIN);
        underTest.deposit(1000);
        underTest.deposit(200);

        underTest.withdraw(500);
        Assertions.assertEquals(700, underTest.check());
    }

    @Test
    void withdrawMoreThanBalance() {
        TerminalServer underTest = new TerminalServerImpl(CORRECT_PIN);
        underTest.deposit(1000);
        underTest.deposit(200);

        Assertions.assertThrows(IllegalAmountException.class, () -> underTest.withdraw(1500));
    }
}
