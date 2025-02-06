package com.sawwere.sber.homework18.terminal.controller;

import com.sawwere.sber.homework18.terminal.exception.AccountIsLockedException;
import com.sawwere.sber.homework18.terminal.exception.AccountIsUnauthorizedException;
import com.sawwere.sber.homework18.terminal.exception.IllegalAmountException;
import com.sawwere.sber.homework18.terminal.service.TerminalService;
import com.sawwere.sber.homework18.terminal.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link TerminalController}
 */
@WebMvcTest({TerminalController.class, CustomErrorController.class})
class TerminalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TerminalService terminalService;
    @MockBean
    private AuthService authService;

    private static final String INVALID_PIN = "4321";

    @BeforeEach
    public void setup() {

    }

    @Test
    public void getBalanceShouldReturn401WhenUnauthorized() throws Exception {
        Mockito.doThrow(new AccountIsUnauthorizedException("message")).when(authService).authorize();
        mockMvc.perform(get((TerminalController.GET_BALANCE)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getBalanceShouldReturn403WhenBlocked() throws Exception {
        Mockito.doThrow(new AccountIsLockedException("message", 1L)).when(authService).authorize();
        mockMvc.perform(get((TerminalController.GET_BALANCE)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getBalance() throws Exception {
        mockMvc.perform(get(TerminalController.GET_BALANCE))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400WhenInvalidAmount() throws Exception {
        Mockito.doThrow(new IllegalAmountException("message")).when(terminalService).deposit(Mockito.anyInt());

        mockMvc.perform(post(TerminalController.DEPOSIT)
                        .param("amount", "-500"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void deposit() throws Exception {
        mockMvc.perform(post(TerminalController.DEPOSIT)
                        .param("amount", "1000"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void withdraw() throws Exception {
        mockMvc.perform(post(TerminalController.DEPOSIT)
                        .param("amount", "1000"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}