package com.sawwere.sber.homework18.terminal.service.auth.state;

import com.sawwere.sber.homework18.terminal.exception.AccountIsLockedException;
import com.sawwere.sber.homework18.terminal.service.auth.AuthService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.sawwere.sber.homework18.terminal.service.auth.AuthService.LOCK_TIMEOUT;

public class BlockedState extends AuthState {
    private final Instant blockTime;

    protected BlockedState(AuthService service) {
        super(service);
        blockTime = Instant.now();
    }

    @Override
    public void authorize() {
        throw new AccountIsLockedException(
                "Аккаунт заблокирован. Операция не может быть выполнена.",
                LOCK_TIMEOUT
        );
    }

    @Override
    public void login(String pinCode) {
        long timeout = Instant.now().minus(blockTime.getEpochSecond(), ChronoUnit.SECONDS).getEpochSecond();
        if (timeout < LOCK_TIMEOUT) {
            throw new AccountIsLockedException(
                    "Аккаунт заблокирован, попытка входа отклонена.",
                    LOCK_TIMEOUT - timeout
            );
        } else {
            service.setState(new UnauthorizedState(service));
            service.login(pinCode);
        }
    }
}
