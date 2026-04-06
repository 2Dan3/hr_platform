package com.hr.management.candidates.model.entity;



import com.hr.management.candidates.model.dto.CandidateRequestDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "candidate")
@Data
@RequiredArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nameFull;
    @Column
    private Date dateOfBirth;
    @Column
    private String contactNumber;
    @Column
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "candidate_skill",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return email.equals(candidate.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public Candidate(CandidateRequestDTO requestDTO) {
        this.nameFull = requestDTO.getNameFull();
        this.dateOfBirth = requestDTO.getDateOfBirth();
        this.contactNumber = requestDTO.getContactNumber();
        this.email = requestDTO.getEmail();
    }
}
