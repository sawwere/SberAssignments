package com.sawwere.sber.homework6.socnet.topic;

/**
 * Сервис для работы с постами их комментариями к ним
 */
public interface TopicService {
    /**
     * Создает новый пост
     * @param topic объект-сущность поста
     * @return объект-сущность созданного поста
     */
    Topic createTopic(Topic topic);

    /**
     * Создает новый ответ к посту
     * @param reply объект-сущность ответа
     * @return объект-сущность созданного ответа
     */
    Reply createReply(Reply reply);

    /**
     * Возвращает пост по его id
     * @param id id поста
     * @return объект-сущность поста
     */
    Topic findById(Long id);
}
