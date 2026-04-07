package com.hr.management.candidates.service;

import com.hr.management.candidates.model.entity.Candidate;
import com.hr.management.candidates.repository.CandidateRepository;

import com.hr.management.candidates.service.impl.CandidateSqlServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateSqlServiceImpl candidateService;

    @Test
    void shouldReturnCandidateById() {

        final String testedName = "Danilo Ugrin";

        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setNameFull(testedName);

        when(candidateRepository.findById(1L))
                .thenReturn(Optional.of(candidate));

        Optional<Candidate> result = candidateService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testedName, result.get().getNameFull());
    }

    @Test
    void shouldSaveCandidate() {

        final String testedName = "Danilo Ugrin";

        Candidate candidate = new Candidate();
        candidate.setNameFull(testedName);

        when(candidateRepository.save(candidate))
                .thenReturn(candidate);

        Candidate saved = candidateService.save(candidate);

        assertNotNull(saved);
        assertEquals(testedName, saved.getNameFull());

        verify(candidateRepository, times(1)).save(candidate);
    }
}