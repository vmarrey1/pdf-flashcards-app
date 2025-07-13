package com.pdfflashcards.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response model for flashcard generation API.
 */
public class FlashcardResponse {
    
    @JsonProperty("flashcards")
    private List<Flashcard> flashcards;
    
    @JsonProperty("totalCards")
    private Integer totalCards;
    
    @JsonProperty("processingTime")
    private Long processingTime;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("message")
    private String message;

    // Default constructor
    public FlashcardResponse() {}

    // Constructor with flashcards
    public FlashcardResponse(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        this.totalCards = flashcards != null ? flashcards.size() : 0;
        this.status = "success";
    }

    // Constructor with all fields
    public FlashcardResponse(List<Flashcard> flashcards, Long processingTime, 
                           String status, String message) {
        this.flashcards = flashcards;
        this.totalCards = flashcards != null ? flashcards.size() : 0;
        this.processingTime = processingTime;
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        this.totalCards = flashcards != null ? flashcards.size() : 0;
    }

    public Integer getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(Integer totalCards) {
        this.totalCards = totalCards;
    }

    public Long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FlashcardResponse{" +
                "flashcards=" + flashcards +
                ", totalCards=" + totalCards +
                ", processingTime=" + processingTime +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
} 