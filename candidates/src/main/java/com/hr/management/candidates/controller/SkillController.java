package com.hr.management.candidates.controller;

import com.hr.management.candidates.model.dto.SkillRequestDTO;
import com.hr.management.candidates.model.dto.SkillResponseDTO;
import com.hr.management.candidates.model.entity.Skill;
import com.hr.management.candidates.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<List<SkillResponseDTO>> getAll() {

        List<Skill> skills = skillService.findAll();
        List<SkillResponseDTO> skillDTOs = skills.stream().map(SkillResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(skillDTOs);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SkillResponseDTO> getSkillById(@Param("id") Long id) {

        Optional<Skill> skill = skillService.findById(id);

        if (!skill.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new SkillResponseDTO(skill.get()));
    }

    @PostMapping(value = "/")
    public ResponseEntity<SkillResponseDTO> createSkill(@RequestBody SkillRequestDTO skillDTO) {

        Skill skill = new Skill(skillDTO);
        skill = skillService.save(skill);

        return ResponseEntity.accepted().body(new SkillResponseDTO(skill));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteSkill(@PathVariable("id") Long id) {

        Optional<Skill> foundSkill = skillService.findById(id);
        if ( !foundSkill.isPresent() ) {
            return ResponseEntity.notFound().build();
        }
        skillService.delete(foundSkill.get());

        return ResponseEntity.ok().build();
    }

//    @PostMapping(value = "/")
//    public ResponseEntity<Skill> create(@RequestBody SkillRequestDTO skillDTO) {
//
//        Skill skill = new Skill(skillDTO);
//        return ResponseEntity.accepted().body(skillService.save(skill));
//    }
}
