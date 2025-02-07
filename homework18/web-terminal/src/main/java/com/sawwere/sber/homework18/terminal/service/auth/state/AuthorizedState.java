package com.sawwere.sber.homework18.terminal.service.auth.state;

import com.sawwere.sber.homework18.terminal.service.auth.AuthService;

public class AuthorizedState extends AuthState {
    protected AuthorizedState(AuthService service) {
        super(service);
    }

    @Override
    public void authorize() {
        // делать ничего не нужно, вход уже был выполнен
    }

    @Override
    public void login(String pinCode) {
        // делать ничего не нужно, вход уже был выполнен
    }
}
