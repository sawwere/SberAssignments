package com.sawwere.sber.homework6.socnet.recommendation;

import com.sawwere.sber.homewrok6.socnet.team.Team;
import com.sawwere.sber.homework6.socnet.topic.Topic;
import com.sawwere.sber.homework6.socnet.user.User;

import java.util.List;

/**
 * Сервис для получения рекомендаций пользователям
 */
public interface RecommendationService {
    /**
     * Возвращает список рекомендуемых "друзей" для пользователя
     * @param userId id пользователя
     * @return новый список
     */
    List<User> getUserRecommendations(Long userId);

    /**
     * Возвращает список рекомендуемых постов для пользователя
     * @param userId id пользователя
     * @return новый список
     */
    List<Topic> getTopicRecommendations(Long userId);

    /**
     * Возвращает список рекомендуемых групп для пользователя
     * @param userId айди пользователя
     * @return новый список
     */
    List<Team> getTeamRecommendations(Long userId);
}
