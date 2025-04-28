package com.backend.similarity.dto;

import java.util.List;
import java.time.LocalDateTime;

public class UserMatchDetailsDTO {
    private UserDetails mainUser;
    private List<MatchedUserDetails> matchedUsers;
    private int totalMatches;

    public static class UserDetails {
        private String username;
        private Integer rank;
        private String submissionId;
        private Integer questionId;
        private String language;
        private LocalDateTime submissionTime;

        public UserDetails(String username, Integer rank, String submissionId, Integer questionId, String language, LocalDateTime submissionTime) {
            this.username = username;
            this.rank = rank;
            this.submissionId = submissionId;
            this.questionId = questionId;
            this.language = language;
            this.submissionTime = submissionTime;
        }

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public String getSubmissionId() {
            return submissionId;
        }

        public void setSubmissionId(String submissionId) {
            this.submissionId = submissionId;
        }

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public LocalDateTime getSubmissionTime() {
            return submissionTime;
        }

        public void setSubmissionTime(LocalDateTime submissionTime) {
            this.submissionTime = submissionTime;
        }
    }

    public static class MatchedUserDetails extends UserDetails {
        private float similarity;

        public MatchedUserDetails(String username, Integer rank, String submissionId, 
                                Integer questionId, String language, float similarity,
                                LocalDateTime submissionTime) {
            super(username, rank, submissionId, questionId, language, submissionTime);
            this.similarity = similarity;
        }

        public float getSimilarity() {
            return similarity;
        }

        public void setSimilarity(float similarity) {
            this.similarity = similarity;
        }
    }

    // Constructor
    public UserMatchDetailsDTO(UserDetails mainUser, List<MatchedUserDetails> matchedUsers) {
        this.mainUser = mainUser;
        this.matchedUsers = matchedUsers;
        this.totalMatches = matchedUsers.size();
    }

    // Getters and setters
    public UserDetails getMainUser() {
        return mainUser;
    }

    public void setMainUser(UserDetails mainUser) {
        this.mainUser = mainUser;
    }

    public List<MatchedUserDetails> getMatchedUsers() {
        return matchedUsers;
    }

    public void setMatchedUsers(List<MatchedUserDetails> matchedUsers) {
        this.matchedUsers = matchedUsers;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }
} 