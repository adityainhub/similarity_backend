package com.backend.similarity.controller;

import com.backend.similarity.dto.MatchDTO;
import com.backend.similarity.dto.SubmissionDTO;
import com.backend.similarity.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionsController {

    private final ResultsService resultsService;

    @Autowired
    public SubmissionsController(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByQuestionId(@PathVariable Integer questionId) {
        List<SubmissionDTO> submissions = resultsService.getUniqueSubmissionsByQuestionId(questionId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/matches")
    public ResponseEntity<List<MatchDTO>> getMatchesByUsernameAndQuestionId(
            @RequestParam String username,
            @RequestParam Integer questionId) {
        List<MatchDTO> matches = resultsService.getMatchesByUsernameAndQuestionId(username, questionId);
        return ResponseEntity.ok(matches);
    }
}