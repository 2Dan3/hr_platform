package com.hr.management.candidates.controller;

import com.hr.management.candidates.model.dto.CandidateRequestDTO;
import com.hr.management.candidates.model.dto.CandidateResponseDTO;
import com.hr.management.candidates.model.dto.SkillAddDTO;
import com.hr.management.candidates.model.entity.Candidate;
import com.hr.management.candidates.model.entity.Skill;
import com.hr.management.candidates.service.CandidateService;
import com.hr.management.candidates.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private SkillService skillService;

    @GetMapping(value = "/index")
    public ModelAndView index() {

        List<Candidate> candidates = candidateService.findAll();
        List<Skill> skills = skillService.findAll();

        ModelAndView mov = new ModelAndView("candidates");
        mov.addObject("candidates", candidates);
        mov.addObject("skills", skills);
        return mov;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<CandidateResponseDTO>> getAllNameOptional(@RequestParam(required = false, value = "name") String name) {

        List<Candidate> candidates;

        if (name == null) {
            candidates = candidateService.findAll();
        }else {
            candidates = candidateService.findAllByName(name);
        }

        List<CandidateResponseDTO> candidateDTOs = candidates.stream().map(CandidateResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(candidateDTOs);
    }

    @GetMapping(value = "/withSkills")
    public ResponseEntity<List<CandidateResponseDTO>> getCandidatesPossessingSkills(@RequestParam(required = false, value = "skillId[]") List<Long> skillIds) {

        List<Candidate> candidates;

        if (skillIds == null || skillIds.isEmpty()) {
            candidates = candidateService.findAll();
        }else {
            candidates = candidateService.findAllPossessingSkills(skillIds);
        }

        List<CandidateResponseDTO> candidateDTOs = candidates.stream().map(CandidateResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(candidateDTOs);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CandidateResponseDTO> getCandidateById(@Param("id") Long id) {

        Optional<Candidate> candidate = candidateService.findById(id);

        if (!candidate.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new CandidateResponseDTO(candidate.get()));
    }

    @PostMapping(value = "/")
    public ResponseEntity<CandidateResponseDTO> createCandidate(@RequestBody CandidateRequestDTO candidateDTO) {

        Candidate candidate = new Candidate(candidateDTO);
        Set<Skill> candidateSkills = candidate.getSkills();

        for (Long skillId : candidateDTO.getSkillIds()) {
            Optional<Skill> foundSkill = skillService.findById(skillId);
            if (foundSkill.isPresent()) {
                candidateSkills.add(foundSkill.get());
            }
        }

        Candidate savedCandidate = candidateService.save(candidate);

        return ResponseEntity.accepted().body(new CandidateResponseDTO(savedCandidate));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCandidate(@PathVariable("id") Long id) {

        Optional<Candidate> foundCandidate = candidateService.findById(id);
        if ( !foundCandidate.isPresent() ) {
            return ResponseEntity.notFound().build();
        }
        candidateService.delete(foundCandidate.get());

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/skills")
    public ResponseEntity<Candidate> addSkillsToCandidate(@PathVariable("id") Long candidateId, @RequestBody List<Long> skillId) {

        Optional<Candidate> candidate = candidateService.findById(candidateId);
        if ( !candidate.isPresent() )
            return ResponseEntity.notFound().build();

        candidateService.addSkillsToCandidate(skillId, candidate.get());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(value = "/{id}/skills")
    public ResponseEntity removeSkillsFromCandidate(@PathVariable("id") Long candidateId, @RequestBody List<Long> skillId) {

        Optional<Candidate> foundCandidate = candidateService.findById(candidateId);
        if ( !foundCandidate.isPresent() ) {
            return ResponseEntity.notFound().build();
        }

        candidateService.removeSkillsFromCandidate(skillId, foundCandidate.get());
        return ResponseEntity.accepted().build();
    }

}
