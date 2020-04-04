package com.dj.service;

import com.dj.po.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface TopicService {
    Page<Topic> listTopic(Pageable pageable, HttpSession session);

    Topic getTopicByName(String name);

    Topic getTopicByNameAndUser(String name,Long id);

    Topic saveTopic(Topic topic);

    Topic getTopicById(Long id);

    Topic updateTopic(Long id,Topic topic);

    void deleteTopic(Long id);
}
