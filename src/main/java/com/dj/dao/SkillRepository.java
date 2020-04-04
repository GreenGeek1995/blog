package com.dj.dao;

import com.dj.po.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SkillRepository extends JpaRepository<Skill,Long> {
    Skill findByName(String name);

    @Query("select s from Skill s where s.user.id=?1")
    Page<Skill> listSkillsByUserId(Long id, Pageable pageable);

    @Query("select s from Skill s where s.name=?1 and s.user.id=?2")
    Skill findByNameAndUser(String name,Long id);
}
