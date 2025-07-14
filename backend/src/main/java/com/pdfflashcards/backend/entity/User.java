package com.pdfflashcards.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String passwordHash;
    private List<String> roles = new ArrayList<>();
    private List<FlashcardHistory> flashcardHistory = new ArrayList<>();

    public User() {}
    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    public List<FlashcardHistory> getFlashcardHistory() { return flashcardHistory; }
    public void setFlashcardHistory(List<FlashcardHistory> flashcardHistory) { this.flashcardHistory = flashcardHistory; }

    public void addFlashcardHistory(FlashcardHistory history) {
        this.flashcardHistory.add(history);
    }

    public static class FlashcardHistory {
        private String id;
        private String title;
        private List<com.pdfflashcards.backend.model.Flashcard> flashcards;
        private long createdAt;

        public FlashcardHistory() {}
        public FlashcardHistory(String title, List<com.pdfflashcards.backend.model.Flashcard> flashcards, long createdAt) {
            this.title = title;
            this.flashcards = flashcards;
            this.createdAt = createdAt;
        }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public List<com.pdfflashcards.backend.model.Flashcard> getFlashcards() { return flashcards; }
        public void setFlashcards(List<com.pdfflashcards.backend.model.Flashcard> flashcards) { this.flashcards = flashcards; }
        public long getCreatedAt() { return createdAt; }
        public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    }
} 