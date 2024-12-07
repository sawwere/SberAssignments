package com.sawwere.sber.homework6.socnet.gift;

import com.sawwere.sber.homework6.socnet.user.User;
import com.sawwere.sber.homework6.socnet.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.mockito.Mockito.when;
class GiftServiceImplTest {
    @Spy
    @InjectMocks
    GiftServiceImpl underTest;
    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(userService.findById(1L)).thenReturn(User.builder()
                .id(1L)
                .username("admin")
                .password("1234")
                .build()
        );
    }

    @Test
    void grantGiftToThemselvesShouldFail() {
        User sender = userService.findById(1L);
        Gift gift = Gift.builder()
                .id(1L)
                .name("super")
                .ownerId(sender.getId())
                .build();
        boolean actual = underTest.grantGift(sender.getId(), gift);

        Assertions.assertFalse(actual);
    }

    @Test
    void findGiftsByOwner() {
        when(underTest.findGiftsByOwner(1L)).thenReturn(
                List.of(
                        Gift.builder().id(1L).build(),
                        Gift.builder().id(2L).build()
                )
        );

        List<Gift> actual = underTest.findGiftsByOwner(1L);

        Assertions.assertEquals(2, actual.size());
    }
}