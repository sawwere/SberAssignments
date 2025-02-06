package com.sawwere.sber.homework18.terminal.service.auth.state;

import com.sawwere.sber.homework18.terminal.service.auth.AuthService;

public abstract class AuthState {
    protected final AuthService service;

    protected AuthState(AuthService service) {
        this.service = service;
    }

    public abstract void authorize();

    public abstract void login(String pinCode);
}
