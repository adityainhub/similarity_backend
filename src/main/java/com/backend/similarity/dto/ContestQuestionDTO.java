package com.backend.similarity.dto;

import com.backend.similarity.model.Difficulty;

public class ContestQuestionDTO {
    private Integer questionId;
    private Integer questionNumber;
    private String title;
    private Difficulty difficulty;
    private Integer points;

    // Constructor
    public ContestQuestionDTO(Integer questionId, Integer questionNumber, String title, 
                            Difficulty difficulty, Integer points) {
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.title = title;
        this.difficulty = difficulty;
        this.points = points;
    }

    // Getters and setters
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
} 