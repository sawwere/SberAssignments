package com.sawwere.sber.homework6.socnet.topic;

import com.sawwere.sber.homework6.socnet.user.User;
import com.sawwere.sber.homework6.socnet.user.UserService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final UserService userService;
    private final List<Topic> topics = new ArrayList<>();
    private Long currentTopicId = 1L;
    private Long currentReplyId = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Topic createTopic(Topic topic) {
        User user = userService.findById(topic.getCreatorId());
        // Тут могли бы быть разные проверки на возможность создания постов этим пользователем
        topic.setId(currentTopicId++);
        topics.add(topic);
        return topic;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Reply createReply(Reply reply) {
        User user = userService.findById(reply.getAuthorId());
        // Тут могли бы быть разные проверки на возможность создания ответа этим пользователем
        Topic topic = findById(reply.getTopic().getId());
        reply.setId(currentReplyId++);
        topic.getReplies().add(reply);
        return reply;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Topic findById(Long id) {
        return topics.stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow();
    }
}
