package com.hr.management.candidates.service.impl;

import com.hr.management.candidates.model.dto.SkillAddDTO;
import com.hr.management.candidates.model.entity.Candidate;
import com.hr.management.candidates.repository.CandidateRepository;
import com.hr.management.candidates.service.CandidateService;
import com.hr.management.candidates.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CandidateSqlServiceImpl implements CandidateService {

    @Autowired
    private SkillService skillService;

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    @Override
    public List<Candidate> findAllBySkillName(String name) {
        return candidateRepository.findAllBySkills_Name(name);
    }

    @Override
    public Optional<Candidate> findById(Long id) {
        return candidateRepository.findById(id);
    }

    @Override
    public List<Candidate> findAllByName(String name) { return candidateRepository.findAllByNameFull(name);}

    @Override
    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public void delete(Candidate candidate) {
        candidateRepository.delete(candidate);
    }

    @Override
    public void addSkillsToCandidate(List<Long> skillId, Candidate candidate) {
//        Optional<Skill> foundSkill;
//        for (SkillAddDTO skillDTO : skillDTOs) {
//
//            foundSkill = skillService.findById(skillDTO.id());
//            if (foundSkill.isPresent()) {
//
//                foundSkill.get().getCandidates().add(candidate);
//                candidate.getSkills().add(foundSkill.get());
//            }
//        }
//        skillService.saveAll()
//
//        return candidateRepository.save(candidate);

        for (Long id : skillId) {
            this.candidateRepository.addSkillToCandidate(id, candidate.getId());
        }

    }

    @Override
    public void removeSkillsFromCandidate(List<SkillAddDTO> skillDTOs, Candidate candidate) {
//        Optional<Skill> foundSkill;
//        for (SkillAddDTO skillDTO : skillDTOs) {
//
//            foundSkill = skillService.findById(skillDTO.id());
//            if (foundSkill.isPresent()) {
//
//                foundSkill.get().getCandidates().add(candidate);
//                candidate.getSkills().add(foundSkill.get());
//            }
//        }
//        skillService.saveAll()
//
//        return candidateRepository.save(candidate);

        for (SkillAddDTO skillDTO : skillDTOs) {
            this.candidateRepository.removeSkillFromCandidate(skillDTO.getId(), candidate.getId());
        }

    }

    @Override
    public List<Candidate> findAllPossessingSkills(List<Long> skillIds) {
        if (skillIds == null || skillIds.isEmpty())
            return candidateRepository.findAll();

        return candidateRepository.findAllPossessingSkills(skillIds, skillIds.size());
    }
}
