package com.sawwere.sber.homework18.terminal.controller;

import com.sawwere.sber.homework18.terminal.service.TerminalService;
import com.sawwere.sber.homework18.terminal.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для обработки запросов к терминалу.
 * Все методы "защищены" и требуют предварительного логина (см. {@link AuthController})
 */
@RestController
@RequiredArgsConstructor
public class TerminalController {
    public static final String GET_BALANCE = "/api/v1/balance";
    public static final String WITHDRAW = "/api/v1/balance/withdraw";
    public static final String DEPOSIT = "/api/v1/balance/deposit";

    private final AuthService authService;
    private final TerminalService terminalService;


    /**
     * Обрабатывает запрос на получение текущего баланса
     * @return целое число - баланс счета
     */
    @GetMapping(GET_BALANCE)
    public Integer getBalance() {
        authService.authorize();
        return terminalService.getBalance();
    }

    /**
     * Обрабатывает запрос на вывод средств со счета
     * @param amount целое неотрицательное и кратное 100 число - сумма снятия
     */
    @PostMapping(WITHDRAW)
    public void withdraw(@RequestParam Integer amount) {
        authService.authorize();
        terminalService.withdraw(amount);
    }

    /**
     * Обрабатывает запрос на пополнение счета
     * @param amount целое неотрицательное и кратное 100 число - сумма пополнения
     */
    @PostMapping(DEPOSIT)
    public void deposit(@RequestParam Integer amount) {
        authService.authorize();
        terminalService.deposit(amount);
    }
}
