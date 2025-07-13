package com.pdfflashcards.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Spring Boot application class for PDF Flashcards backend.
 * Provides REST API endpoints for PDF processing and flashcard generation.
 */
@SpringBootApplication
public class PdfFlashcardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfFlashcardsApplication.class, args);
    }

    /**
     * Configure CORS to allow frontend requests from React development server
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
} 