package com.hr.management.candidates.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SkillAddDTO {

    private Long id;

    public SkillAddDTO(Long id){
        this.id = id;
    }
}
