package com.dj.dao;

import com.dj.po.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TopicRepository extends JpaRepository<Topic,Long> {
    Topic findByName(String name);

    @Query("select t from Topic t where t.user.id=?1")
    Page<Topic> listTopicsByUserId(Long id, Pageable pageable);

    @Query("select t from Topic t where t.name=?1 and t.user.id=?2")
    Topic findByNameAndUser(String name,Long id);
}
