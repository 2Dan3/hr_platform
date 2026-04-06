package com.hr.management.candidates.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SkillRequestDTO {

    private String name;

    public SkillRequestDTO(String name){
        this.name = name;
    }
}
