package com.sawwere.sber.homewrok6.socnet.team;

import com.sawwere.sber.homework6.socnet.user.User;
import com.sawwere.sber.homework6.socnet.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class TeamServiceImplTest {
    @Spy
    @InjectMocks
    TeamServiceImpl underTest;

    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = User.builder()
                .id(1L)
                .username("admin")
                .password("1234")
                .build();

        when(userService.findById(1L)).thenReturn(user);
        when(userService.findById(2L)).thenReturn(User.builder()
                .id(2L)
                .username("user2")
                .password("1234")
                .build()
        );
        when(userService.findById(3L)).thenReturn(User.builder()
                .id(3L)
                .username("user3")
                .password("1234")
                .build()
        );


        underTest.create(Team.builder()
                .name("team1")
                .creatorId(user.getId())
                .members(new ArrayList<>(List.of(user.getId())))
                .build()
        );
    }

    @Test
    void findById() {
        Team expected = Team.builder()
                .id(1L)
                .name("team1")
                .creatorId(1L)
                .members(List.of(1L))
                .build();
        Team actual = underTest.findById(1L);
        Assertions.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void findAll() {
        List<Team> actual = underTest.findAll();
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void findByUser() {
        List<Team> actual = underTest.findByUser(1L);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void addMember() {
        boolean actual = underTest.addMember(1L, 2L);
        Assertions.assertTrue(actual);
    }
}