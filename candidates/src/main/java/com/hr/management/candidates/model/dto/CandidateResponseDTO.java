package com.hr.management.candidates.model.dto;

import com.hr.management.candidates.model.entity.Candidate;
import com.hr.management.candidates.model.entity.Skill;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//public record CandidateResponseDTO(Long id, String nameFull, Date dateOfBirth, String contactNumber, String email, List<SkillResponseDTO> skills) {
@Data
@RequiredArgsConstructor
public class CandidateResponseDTO {
    private Long id;
    private String nameFull;
    private Date dateOfBirth;
    private String contactNumber;
    private String email;
    private List<SkillResponseDTO> skills = new ArrayList<>();

    public CandidateResponseDTO(Candidate candidate) {
        this.id = candidate.getId();
        this.nameFull = candidate.getNameFull();
        this.dateOfBirth = candidate.getDateOfBirth();
        this.contactNumber = candidate.getContactNumber();
        this.email = candidate.getEmail();
        this.skills = candidate.getSkills().stream().map(SkillResponseDTO::new).collect(Collectors.toList());
    }
}
