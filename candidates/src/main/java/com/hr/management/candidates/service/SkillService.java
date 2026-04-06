package com.hr.management.candidates.service;

import com.hr.management.candidates.model.entity.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    void delete(Skill skill);

    Optional<Skill> findById(Long id);

    Skill save(Skill skill);

    List<Skill> saveAll(List<Skill> skills);

    List<Skill> findAll();
}
