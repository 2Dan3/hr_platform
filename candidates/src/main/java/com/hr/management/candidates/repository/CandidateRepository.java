package com.hr.management.candidates.repository;

import com.hr.management.candidates.model.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {


    List<Candidate> findAllBySkills_Name(String name);

    List<Candidate> findAllByNameFullContainingIgnoreCase(String name);

    @Query(
            nativeQuery = true,
            value = "INSERT INTO candidate_skill VALUES (:candidateId, :skillId);"
    )
    void addSkillToCandidate(@Param("skillId") Long skillId, @Param("candidateId") Long candidateId);

    @Query(
            nativeQuery = true,
            value = "DELETE FROM candidate_skill WHERE candidateId = :candidateId AND skillId = :skillId);"
    )
    void removeSkillFromCandidate(@Param("skillId") Long skillId, @Param("candidateId") Long candidateId);

    @Query(value =
        "SELECT c FROM Candidate c " +
        "JOIN c.skills s " +
        "WHERE s.id IN :skills " +
        "GROUP BY c.id " +
        "HAVING COUNT(DISTINCT s.id) = :size"
    )
    List<Candidate> findAllPossessingSkills(@Param("skills") List<Long> skillIds, @Param("size") long size);
}
