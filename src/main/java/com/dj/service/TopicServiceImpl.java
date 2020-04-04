package com.dj.service;

import com.dj.NotFoundException;
import com.dj.dao.TopicRepository;
import com.dj.po.Topic;
import com.dj.po.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Override
    public Page<Topic> listTopic(Pageable pageable, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return topicRepository.listTopicsByUserId(user.getId(),pageable);
    }

    @Override
    public Topic getTopicByName(String name) {
        return topicRepository.findByName(name);
    }

    @Override
    public Topic getTopicByNameAndUser(String name, Long id) {
        return topicRepository.findByNameAndUser(name,id);
    }

    @Transactional
    @Override
    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic getTopicById(Long id) {
        return topicRepository.getOne(id);
    }

    @Transactional
    @Override
    public Topic updateTopic(Long id, Topic topic) {
        Topic t = topicRepository.getOne(id);
        if(t == null){
            throw new NotFoundException("该主题不存在");
        }
        BeanUtils.copyProperties(topic,t);
        return topicRepository.save(t);
    }

    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}
