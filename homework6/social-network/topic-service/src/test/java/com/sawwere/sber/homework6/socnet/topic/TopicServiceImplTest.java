package com.sawwere.sber.homework6.socnet.topic;

import com.sawwere.sber.homework6.socnet.user.User;
import com.sawwere.sber.homework6.socnet.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.when;

class TopicServiceImplTest {
    @Spy
    @InjectMocks
    TopicServiceImpl underTest;

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


        underTest.createTopic(Topic.builder()
                .name("topic1")
                .text("abracadabra")
                .creatorId(user.getId())
                .build()
        );
    }

    @Test
    void createTopic() {
        Topic actual = underTest.createTopic(Topic.builder()
                .name("topic2")
                .text("text")
                .creatorId(1L)
                .build()
        );
        Assertions.assertEquals(2L, actual.getId());
        Assertions.assertEquals("topic2", actual.getName());
    }

    @Test
    void createReply() {
        Reply actual = underTest.createReply(Reply.builder()
                .text("reply text")
                .topic(underTest.findById(1L))
                .authorId(1L)
                .build()
        );
        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertEquals("reply text", actual.getText());
    }

    @Test
    void findById() {
        Topic actual = underTest.findById(1L);
        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertEquals("topic1", actual.getName());
    }
}