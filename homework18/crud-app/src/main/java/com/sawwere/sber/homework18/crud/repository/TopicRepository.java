package com.sawwere.sber.homework18.crud.repository;

import com.sawwere.sber.homework18.crud.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

}