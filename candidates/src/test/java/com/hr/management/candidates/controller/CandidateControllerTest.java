package com.hr.management.candidates.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.management.candidates.model.entity.Candidate;
import com.hr.management.candidates.service.CandidateService;
import com.hr.management.candidates.service.SkillService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CandidateController.class)
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateService candidateService;

    @MockBean
    private SkillService skillService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllCandidates_whenNoNameProvided() throws Exception {

        final String[] names = {"Danilo Ugrin", "Ivana Ivanovic", "Marko Markovic"};

        Candidate candidate1 = new Candidate();
        candidate1.setNameFull(names[0]);

        Candidate candidate2 = new Candidate();
        candidate2.setNameFull(names[1]);

        Candidate candidate3 = new Candidate();
        candidate3.setNameFull(names[2]);

        Mockito.when(candidateService.findAll())
                .thenReturn(Arrays.asList(candidate1, candidate2, candidate3));

        mockMvc.perform(get("/api/candidates/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(names.length))
                .andExpect(jsonPath("$[0].nameFull").value(names[0]))
                .andExpect(jsonPath("$[1].nameFull").value(names[1]))
                .andExpect(jsonPath("$[2].nameFull").value(names[2]));
    }

    @Test
    void shouldFilterByName_whenNameProvided() throws Exception {

        final String searchTerm = "ovic";

        Candidate c1 = new Candidate();
        c1.setNameFull("Ivana Ivanovic");

        Candidate c2 = new Candidate();
        c2.setNameFull("Marko Markovic");

        Mockito.when(candidateService.findAllByName(searchTerm))
                .thenReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get("/api/candidates/all")
                        .param("name", searchTerm))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nameFull").value("Ivana Ivanovic"))
                .andExpect(jsonPath("$[1].nameFull").value("Marko Markovic"));;
    }
}