package com.sawwere.sber.homework18.terminal.controller;

import com.sawwere.sber.homework18.terminal.exception.AccountIsLockedException;
import com.sawwere.sber.homework18.terminal.exception.AccountIsUnauthorizedException;
import com.sawwere.sber.homework18.terminal.service.TerminalService;
import com.sawwere.sber.homework18.terminal.service.auth.AuthService;
import com.sawwere.sber.homework18.terminal.service.auth.state.UnauthorizedState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link AuthController}
 */
@WebMvcTest({AuthController.class, CustomErrorController.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;

    private static final String INVALID_PIN = "4321";

    @BeforeEach
    public void setup() {

    }

    @Test
    public void unauthorizedAccountExceptionShouldReturn401() throws Exception {
        Mockito.doThrow(new AccountIsUnauthorizedException("message")).when(authService).login(INVALID_PIN);
        mockMvc.perform(get((AuthController.LOGIN))
                .param("pin", INVALID_PIN))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void lockedAccountExceptionShouldReturn403() throws Exception {
        Mockito.doThrow(new AccountIsLockedException("message", 1)).when(authService).login(INVALID_PIN);
        mockMvc.perform(get((AuthController.LOGIN))
                        .param("pin", INVALID_PIN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void validPinIsOk() throws Exception {
        mockMvc.perform(get(AuthController.LOGIN)
                        .param("pin", AuthService.CORRECT_PIN_CODE))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
