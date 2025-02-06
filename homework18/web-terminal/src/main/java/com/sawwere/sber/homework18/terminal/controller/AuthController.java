package com.sawwere.sber.homework18.terminal.controller;

import com.sawwere.sber.homework18.terminal.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов на аутентификацию
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
    public static final String LOGIN = "/api/v1/auth/login";

    private final AuthService authService;

    @GetMapping(LOGIN)
    public void login(@RequestParam String pin) {
        authService.login(pin);
    }
}
