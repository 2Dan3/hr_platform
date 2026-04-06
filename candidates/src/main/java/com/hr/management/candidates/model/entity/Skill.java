package com.hr.management.candidates.model.entity;

import com.hr.management.candidates.model.dto.SkillRequestDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "skill")
@Data
@RequiredArgsConstructor
public class Skill {

    @Id
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Set<Candidate> candidates = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return name.equals(skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Skill(SkillRequestDTO requestDTO) {
        this.name = requestDTO.getName();
    }
}
