package com.hr.management.candidates.repository;

import com.hr.management.candidates.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {



}
