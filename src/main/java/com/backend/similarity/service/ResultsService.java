package com.backend.similarity.service;

import com.backend.similarity.dto.MatchDTO;
import com.backend.similarity.dto.SubmissionDTO;
import com.backend.similarity.dto.UserMatchDetailsDTO;
import com.backend.similarity.model.Results;
import com.backend.similarity.repository.ResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultsService {

    private final ResultsRepository resultsRepository;

    @Autowired
    public ResultsService(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    public List<SubmissionDTO> getUniqueSubmissionsByQuestionId(Integer questionId) {
        List<Object[]> resultData = resultsRepository.findSubmissionDataByQuestionId(questionId);
        List<SubmissionDTO> submissions = new ArrayList<>();

        for (Object[] row : resultData) {
            String username = (String) row[0];
            Integer rank = (Integer) row[1];
            String language = (String) row[2];
            LocalDateTime submissionTime = (LocalDateTime) row[3];

            submissions.add(new SubmissionDTO(username, rank, language, submissionTime));
        }

        return submissions.stream()
                .collect(Collectors.groupingBy(SubmissionDTO::getUsername))
                .values()
                .stream()
                .map(userSubmissions -> userSubmissions.stream()
                        .min(Comparator.comparing(SubmissionDTO::getSubmissionTime, Comparator.nullsLast(Comparator.naturalOrder())))
                        .orElse(null)) // If no submissions exist, return null
                .collect(Collectors.toList());
    }

    public List<MatchDTO> getMatchesByUsernameAndQuestionId(String username, Integer questionId) {
        List<Results> results = resultsRepository.findByQuestionAndUsername(questionId, username);

        return results.stream()
                .map(result -> {
                    // Determine which username is the one we're looking for
                    boolean isUsername1 = result.getUsername1().equals(username);

                    return new MatchDTO(
                            result.getSubmissionId1(),
                            result.getSubmissionId2(),
                            result.getUsername1(),
                            result.getUsername2(),
                            result.getAverageSimilarity(),
                            result.getLanguage(),
                            result.getRank1(),
                            result.getRank2(),
                            result.getSubmission1Time(),
                            result.getSubmission2Time()
                    );
                })
                .collect(Collectors.toList());
    }

    public UserMatchDetailsDTO getContestQuestionMatches(String contestId, Integer questionId, String username) {
        List<Results> results = resultsRepository.findByContestQuestionAndUsername(contestId, questionId, username);
        
        // Create main user details from the first match
        UserMatchDetailsDTO.UserDetails mainUser = null;
        List<UserMatchDetailsDTO.MatchedUserDetails> matchedUsers = new ArrayList<>();
        
        for (Results result : results) {
            boolean isUsername1 = result.getUsername1().equals(username);
            
            // Set main user details if not set
            if (mainUser == null) {
                mainUser = new UserMatchDetailsDTO.UserDetails(
                    username,
                    isUsername1 ? result.getRank1() : result.getRank2(),
                    isUsername1 ? result.getSubmissionId1() : result.getSubmissionId2(),
                    result.getQuestion().getQuestionId(),
                    result.getLanguage(),
                    isUsername1 ? result.getSubmission1Time() : result.getSubmission2Time()

                );
            }
            
            // Add matched user details with similarity and language
            UserMatchDetailsDTO.MatchedUserDetails matchedUser = new UserMatchDetailsDTO.MatchedUserDetails(
                isUsername1 ? result.getUsername2() : result.getUsername1(),
                isUsername1 ? result.getRank2() : result.getRank1(),
                isUsername1 ? result.getSubmissionId2() : result.getSubmissionId1(),
                result.getQuestion().getQuestionId(),
                result.getLanguage(),
                result.getAverageSimilarity(),
                isUsername1 ? result.getSubmission2Time() : result.getSubmission1Time()

            );
            matchedUsers.add(matchedUser);
        }
        
        // If no results found, create empty main user details
        if (mainUser == null) {
            mainUser = new UserMatchDetailsDTO.UserDetails(username, null, null, questionId, null, null);

        }
        
        return new UserMatchDetailsDTO(mainUser, matchedUsers);
    }
}