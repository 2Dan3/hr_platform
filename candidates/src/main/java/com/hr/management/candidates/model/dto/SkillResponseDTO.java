package com.hr.management.candidates.model.dto;

import com.hr.management.candidates.model.entity.Skill;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SkillResponseDTO {

    private Long id;
    private String name;

    public SkillResponseDTO(Skill skill) {
        this.id = skill.getId();
        this.name = skill.getName();
    }
}
