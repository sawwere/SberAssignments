package com.sawwere.sber.homework6.socnet.recommendation;

import com.sawwere.sber.homewrok6.socnet.team.Team;
import com.sawwere.sber.homewrok6.socnet.team.TeamService;
import com.sawwere.sber.homework6.socnet.topic.Topic;
import com.sawwere.sber.homework6.socnet.topic.TopicService;
import com.sawwere.sber.homework6.socnet.user.User;
import com.sawwere.sber.homework6.socnet.user.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService{
    private final UserService userService;
    private final TopicService topicService;
    private final TeamService teamService;

    /**
     * {@inheritDoc}
     * @implNote  Да, рекомендательная система требует доработки:(
     */
    @Override
    public List<User> getUserRecommendations(Long userId) {
        return List.of(userService.findById(1L));
    }

    /**
     * {@inheritDoc}
     * @implNote  Да, рекомендательная система требует доработки:(
     */
    @Override
    public List<Topic> getTopicRecommendations(Long userId) {
        return List.of(topicService.findById(1L));
    }

    /**
     * {@inheritDoc}
     * @implNote  Да, рекомендательная система требует доработки:(
     */
    @Override
    public List<Team> getTeamRecommendations(Long userId) {
        return List.of(teamService.findById(1L));
    }
}
