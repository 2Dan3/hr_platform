package com.hr.management.candidates.service;

import com.hr.management.candidates.model.entity.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateService {
    List<Candidate> findAll();
    List<Candidate> findAllBySkillName(String name);

    Optional<Candidate> findById(Long id);

    List<Candidate> findAllByName(String name);

    Candidate save(Candidate candidate);

    void delete(Candidate candidate);

    void addSkillsToCandidate(List<Long> skillId, Candidate candidate);

    void removeSkillsFromCandidate(List<Long> skillId, Candidate candidate);

    List<Candidate> findAllPossessingSkills(List<Long> skillIds);
}
