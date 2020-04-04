package com.dj.service;

import com.dj.NotFoundException;
import com.dj.dao.SkillRepository;
import com.dj.po.Skill;
import com.dj.po.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillRepository skillRepository;
    @Override
    public Page<Skill> listSkill(Pageable pageable, HttpSession session) {
        User user = (User) session.getAttribute("user");
        return skillRepository.listSkillsByUserId(user.getId(),pageable);
    }

    @Override
    public Skill getSkillByName(String name) {
        return skillRepository.findByName(name);
    }

    @Override
    public Skill getSkillByNameAndUser(String name, Long id) {
        return skillRepository.findByNameAndUser(name,id);
    }

    @Transactional
    @Override
    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Skill getSkillById(Long id) {
        return skillRepository.getOne(id);
    }

    @Transactional
    @Override
    public Skill updateSkill(Long id, Skill skill) {
        Skill s = skillRepository.getOne(id);
        if(s == null){
            throw new NotFoundException("该主题不存在");
        }
        BeanUtils.copyProperties(skill,s);
        return skillRepository.save(s);
    }

    @Override
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}
