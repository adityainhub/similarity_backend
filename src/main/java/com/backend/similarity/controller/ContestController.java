package com.backend.similarity.controller;

import com.backend.similarity.dto.ContestDTO;
import com.backend.similarity.dto.ContestQuestionDTO;
import com.backend.similarity.dto.UserMatchDetailsDTO;
import com.backend.similarity.service.ContestService;
import com.backend.similarity.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contests")
public class ContestController {

    private final ContestService contestService;
    private final ResultsService resultsService;

    @Autowired
    public ContestController(ContestService contestService, ResultsService resultsService) {
        this.contestService = contestService;
        this.resultsService = resultsService;
    }

    @GetMapping
    public ResponseEntity<List<ContestDTO>> getAllContests() {
        List<ContestDTO> contests = contestService.getAllContests();
        return ResponseEntity.ok(contests);
    }

    @GetMapping("/{contestId}")
    public ResponseEntity<ContestDTO> getContestById(@PathVariable String contestId) {
        ContestDTO contest = contestService.getContestById(contestId);
        if (contest != null) {
            return ResponseEntity.ok(contest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/username-search/{username}")
    public ResponseEntity<?> getContestsByUsername(@PathVariable String username) {
        try {
            List<Map<String, Object>> contests = contestService.getContestsByUsername(username);
            return ResponseEntity.ok(contests);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error retrieving contests: " + e.getMessage());
        }
    }

    @GetMapping("/username-search/{username}/{contestId}")
    public ResponseEntity<?> getContestQuestionsForUser(
            @PathVariable String username,
            @PathVariable String contestId) {
        try {
            List<ContestQuestionDTO> questions = contestService.getContestQuestionsForUser(contestId, username);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error retrieving questions: " + e.getMessage());
        }
    }

    @GetMapping("/username-search/{username}/{contestId}/{questionId}")
    public ResponseEntity<?> getContestQuestionMatches(
            @PathVariable String username,
            @PathVariable String contestId,
            @PathVariable Integer questionId) {
        try {
            UserMatchDetailsDTO matches = resultsService.getContestQuestionMatches(contestId, questionId, username);
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error retrieving match details: " + e.getMessage());
        }
    }
}