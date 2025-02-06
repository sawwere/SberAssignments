package com.sawwere.sber.homework18.terminal.service.auth;

import com.sawwere.sber.homework18.terminal.service.auth.state.AuthState;
import com.sawwere.sber.homework18.terminal.service.auth.state.UnauthorizedState;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class AuthService {
    public static final int ERROR_LIMIT = 3;
    public static final long LOCK_TIMEOUT = 10L;
    public static final String CORRECT_PIN_CODE = "1234";

    private AuthState state;

    public void setState(AuthState state) {
        this.state = state;
    }

    public AuthService() {
        this.state = new UnauthorizedState(this);
    }

    /**
     * Выполняет вход в аккаунт
     * @param pinCode пин-код пользователя
     */
    public void login(String pinCode) {
        state.login(pinCode);
    }

    /**
     * Проверяет степень аутентификации пользователя.
     * Выбрасывает исключение, если текущее состояние не равно "Авторизован"
     */
    public void authorize() {
        state.authorize();
    }
}
