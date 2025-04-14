package com.backend.similarity.service;

import com.backend.similarity.dto.ContestDTO;
import com.backend.similarity.dto.ContestQuestionDTO;
import com.backend.similarity.model.Contest;
import com.backend.similarity.model.Question;
import com.backend.similarity.model.Results;
import com.backend.similarity.repository.ContestRepository;
import com.backend.similarity.repository.ResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ContestService {

    private final ContestRepository contestRepository;
    private final ResultsRepository resultsRepository;

    @Autowired
    public ContestService(ContestRepository contestRepository, ResultsRepository resultsRepository) {
        this.contestRepository = contestRepository;
        this.resultsRepository = resultsRepository;
    }

    public List<ContestDTO> getAllContests() {
        return contestRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ContestDTO getContestById(String contestId) {
        Optional<Contest> contest = contestRepository.findById(contestId);
        return contest.map(this::convertToDTO).orElse(null);
    }

    public List<Map<String, Object>> getContestsByUsername(String username) {
        // Find all results where username matches either username1 or username2
        List<Results> results = resultsRepository.findByUsername1OrUsername2(username, username);
        
        // Create a map to store unique contests
        Map<String, Map<String, Object>> contestData = new HashMap<>();
        
        for (Results result : results) {
            String contestId = result.getContestId();
            
            // If we haven't processed this contest yet
            if (!contestData.containsKey(contestId)) {
                Contest contest = contestRepository.findByContestId(contestId)
                    .orElseThrow(() -> new RuntimeException("Contest not found: " + contestId));
                
                Map<String, Object> contestInfo = new HashMap<>();
                contestInfo.put("contestId", contestId);
                contestInfo.put("contestTitle", contest.getTitle());
                contestInfo.put("contestDate", contest.getStartDate());
                contestInfo.put("participantCount", contest.getParticipantCount());
                
                contestData.put(contestId, contestInfo);
            }
        }
        
        return new ArrayList<>(contestData.values());
    }

    public List<ContestQuestionDTO> getContestQuestionsForUser(String contestId, String username) {
        List<Question> questions = resultsRepository.findDistinctQuestionsByContestAndUsername(contestId, username);
        
        return questions.stream()
            .map(q -> new ContestQuestionDTO(
                q.getQuestionId(),
                q.getQuestionNumber(),
                q.getTitle(),
                q.getDifficulty(),
                q.getPoint()
            ))
            .collect(Collectors.toList());
    }

    private ContestDTO convertToDTO(Contest contest) {
        return new ContestDTO(
                contest.getContestId(),
                contest.getTitle(),
                contest.getStartDate(),
                contest.getParticipantCount()
        );
    }
}