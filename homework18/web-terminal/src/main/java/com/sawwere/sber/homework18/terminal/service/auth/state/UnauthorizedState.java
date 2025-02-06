package com.sawwere.sber.homework18.terminal.service.auth.state;

import com.sawwere.sber.homework18.terminal.exception.AccountIsLockedException;
import com.sawwere.sber.homework18.terminal.exception.AccountIsUnauthorizedException;
import com.sawwere.sber.homework18.terminal.exception.InvalidCredentialsException;
import com.sawwere.sber.homework18.terminal.service.auth.AuthService;

import static com.sawwere.sber.homework18.terminal.service.auth.AuthService.*;

public class UnauthorizedState extends AuthState {
    public UnauthorizedState(AuthService service) {
        super(service);
    }

    private int errorCount;

    @Override
    public void authorize() {
        throw new AccountIsUnauthorizedException("Для выполнения требуемого запроса требуется выполнить вход");
    }

    @Override
    public void login(String pinCode) {
        if (!pinCode.equals(CORRECT_PIN_CODE)) {
            errorCount += 1;
            if (errorCount == ERROR_LIMIT) {
                service.setState(new BlockedState(service));
                throw new AccountIsLockedException("Превышен лимит попыток входа, аккаунт заблокирован.", LOCK_TIMEOUT);
            }
            throw new InvalidCredentialsException("Неправильный пин-код.");
        }
        service.setState(new AuthorizedState(service));
    }
}
