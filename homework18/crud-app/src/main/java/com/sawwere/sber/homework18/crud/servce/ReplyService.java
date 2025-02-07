package com.sawwere.sber.homework18.crud.servce;

import com.sawwere.sber.homework18.crud.domain.Reply;
import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.dto.ReplyCreationDto;
import com.sawwere.sber.homework18.crud.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final TopicService topicService;

    private final ReplyRepository replyRepository;

    @Transactional
    public Reply create(ReplyCreationDto dto) {
        Topic topic = topicService.findByIdOrElseThrow(dto.getTopicId());
        Reply reply = Reply.builder()
                .text(dto.getText())
                .author(dto.getAuthor())
                .topic(topic)
                .build();
        topic.getReplies().add(reply);
        return replyRepository.save(reply);
    }

    public void delete(Long id) {
        replyRepository.deleteById(id);
    }
}
