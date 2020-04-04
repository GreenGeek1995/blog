package com.dj.service;

import com.dj.po.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface SkillService {
    Page<Skill> listSkill(Pageable pageable, HttpSession session);

    Skill getSkillByName(String name);

    Skill getSkillByNameAndUser(String name,Long id);

    Skill saveSkill(Skill skill);

    Skill getSkillById(Long id);

    Skill updateSkill(Long id, Skill skill);

    void deleteSkill(Long id);
}
