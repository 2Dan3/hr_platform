package com.hr.management.candidates.service.impl;

import com.hr.management.candidates.model.entity.Skill;
import com.hr.management.candidates.repository.SkillRepository;
import com.hr.management.candidates.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class SkillSqlServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;


    @Override
    public void delete(Skill skill) {
        skillRepository.delete(skill);
    }

    @Override
    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public List<Skill> saveAll(List<Skill> skills) {
        return skillRepository.saveAll(skills);
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }
}
