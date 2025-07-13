package com.pdfflashcards.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class representing a flashcard with question, answer, and optional explanation.
 */
public class Flashcard {
    
    @JsonProperty("question")
    private String question;
    
    @JsonProperty("answer")
    private String answer;
    
    @JsonProperty("explanation")
    private String explanation;
    
    @JsonProperty("confidence")
    private Double confidence;
    
    @JsonProperty("sourcePage")
    private Integer sourcePage;
    
    @JsonProperty("sourceText")
    private String sourceText;

    // Default constructor
    public Flashcard() {}

    // Constructor with required fields
    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // Constructor with all fields
    public Flashcard(String question, String answer, String explanation, 
                    Double confidence, Integer sourcePage, String sourceText) {
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.confidence = confidence;
        this.sourcePage = sourcePage;
        this.sourceText = sourceText;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Integer getSourcePage() {
        return sourcePage;
    }

    public void setSourcePage(Integer sourcePage) {
        this.sourcePage = sourcePage;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", explanation='" + explanation + '\'' +
                ", confidence=" + confidence +
                ", sourcePage=" + sourcePage +
                ", sourceText='" + sourceText + '\'' +
                '}';
    }
} 