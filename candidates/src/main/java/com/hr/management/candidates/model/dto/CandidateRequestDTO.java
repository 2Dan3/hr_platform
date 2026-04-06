package com.hr.management.candidates.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CandidateRequestDTO{

    private String nameFull;
    private Date dateOfBirth;
    private String contactNumber;
    private String email;
    private List<Long> skillIds = new ArrayList<>();

    public CandidateRequestDTO(
        String nameFull,
        Date dateOfBirth,
        String contactNumber,
        String email,
        List<Long> skillIds) {

        this.nameFull = nameFull;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.email = email;
        this.skillIds = skillIds;
    }
}
