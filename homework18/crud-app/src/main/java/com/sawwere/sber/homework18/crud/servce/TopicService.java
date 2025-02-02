package com.sawwere.sber.homework18.crud.servce;

import com.sawwere.sber.homework18.crud.domain.Topic;
import com.sawwere.sber.homework18.crud.domain.exception.NotFoundException;
import com.sawwere.sber.homework18.crud.dto.TopicCreationDto;
import com.sawwere.sber.homework18.crud.mapper.TopicMapper;
import com.sawwere.sber.homework18.crud.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    @Transactional(readOnly = true)
    public Topic findByIdOrElseThrow(Long id) {
        return topicRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пост c id %s не найден".formatted(id))
        );
    }

    @Transactional(readOnly = true)
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Transactional
    public Topic create(TopicCreationDto dto) {
        Topic topic = topicMapper.mapToEntity(dto);
        topicRepository.save(topic);
        return topic;
    }

    @Transactional
    public void delete(Long id) {
        topicRepository.deleteById(id);
    }

    @Transactional
    public Topic update(Long id, TopicCreationDto dto) {
        if (!topicRepository.existsById(id)) {
            throw new NotFoundException("Пост c id %s не найден".formatted(id));
        }
        Topic topic = findByIdOrElseThrow(id);
        topic.setAuthor(dto.getAuthor());
        topic.setTitle(dto.getTitle());
        topic.setText(dto.getText());
        return topicRepository.save(topic);
    }
}
