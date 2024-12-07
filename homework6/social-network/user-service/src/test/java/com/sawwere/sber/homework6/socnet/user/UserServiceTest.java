package com.sawwere.sber.homework6.socnet.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    UserService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User user1 = User.builder()
                .id(1L)
                .username("name")
                .password("4321")
                .build();
        User user2 = User.builder()
                .id(2L)
                .username("otherUser")
                .password("1234")
                .build();
        when(underTest.findById(1L)).thenReturn(user1);
        when(underTest.findById(2L)).thenReturn(user2);
        when(underTest.addSubscription(user1, user2)).thenReturn(true);
    }

    @Test
    void findById() {
        User actual = underTest.findById(1L);
        Assertions.assertEquals("name", actual.getUsername());
    }

    @Test
    void addSubscription() {
        boolean actual = underTest.addSubscription(underTest.findById(1L), underTest.findById(2L));
        Assertions.assertTrue(actual);
    }
}