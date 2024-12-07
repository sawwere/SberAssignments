package com.sawwere.sber.homework6.socnet.recommendation;

import com.sawwere.sber.homework6.socnet.topic.Topic;
import com.sawwere.sber.homework6.socnet.topic.TopicService;
import com.sawwere.sber.homework6.socnet.user.User;
import com.sawwere.sber.homework6.socnet.user.UserService;
import com.sawwere.sber.homewrok6.socnet.team.Team;
import com.sawwere.sber.homewrok6.socnet.team.TeamService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;
import static org.mockito.Mockito.when;

class RecommendationServiceImplTest {
    @Spy
    @InjectMocks
    RecommendationServiceImpl underTest;
    @Mock
    UserService userService;
    @Mock
    TopicService topicService;
    @Mock
    TeamService teamService;

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

        when(teamService.findById(1L)).thenReturn(Team.builder()
                .id(1L)
                .name("team1")
                .creatorId(user.getId())
                .members(List.of(user.getId()))
                .build()
        );

        when(topicService.findById(1L)).thenReturn(Topic.builder()
                .id(1L)
                .name("super topic")
                .text("text of super topic")
                .creatorId(user.getId())
                .build()
        );
    }

    @Test
    void getUserRecommendations() {
        User sender = userService.findById(1L);
        List<User> expected = List.of(userService.findById(2L), userService.findById(3L));
        when(underTest.getUserRecommendations(sender.getId())).thenReturn(expected);

        List<User> actual = underTest.getUserRecommendations(sender.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTopicRecommendations() {
        User sender = userService.findById(1L);
        List<Topic> expected = List.of(topicService.findById(1L));
        when(underTest.getTopicRecommendations(sender.getId())).thenReturn(expected);

        List<Topic> actual = underTest.getTopicRecommendations(sender.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTeamRecommendations() {
        User sender = userService.findById(1L);
        List<Team> expected = List.of(teamService.findById(1L));
        when(underTest.getTeamRecommendations(sender.getId())).thenReturn(expected);

        List<Team> actual = underTest.getTeamRecommendations(sender.getId());

        Assertions.assertEquals(expected, actual);
    }
}