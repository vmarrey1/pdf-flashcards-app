package com.pdfflashcards.backend.controller;

import com.pdfflashcards.backend.model.FlashcardResponse;
import com.pdfflashcards.backend.service.FlashcardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * REST controller for PDF flashcard generation endpoints.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class FlashcardController {
    
    private static final Logger logger = LoggerFactory.getLogger(FlashcardController.class);
    
    @Autowired
    private FlashcardService flashcardService;
    
    /**
     * Process PDF file and generate flashcards.
     * 
     * @param file The PDF file to process
     * @return ResponseEntity containing generated flashcards
     */
    @PostMapping("/process-pdf")
    public ResponseEntity<FlashcardResponse> processPdf(@RequestParam("pdf") MultipartFile file) {
        logger.info("Received PDF processing request for file: {}", file.getOriginalFilename());
        
        try {
            FlashcardResponse response = flashcardService.processPdfAndGenerateFlashcards(file);
            
            if ("success".equals(response.getStatus())) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IOException e) {
            logger.error("IO error processing PDF: {}", file.getOriginalFilename(), e);
            FlashcardResponse errorResponse = new FlashcardResponse(
                null, 0L, "error", "IO error: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request: {}", e.getMessage());
            FlashcardResponse errorResponse = new FlashcardResponse(
                null, 0L, "error", "Invalid request: " + e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            logger.error("Unexpected error processing PDF: {}", file.getOriginalFilename(), e);
            FlashcardResponse errorResponse = new FlashcardResponse(
                null, 0L, "error", "Unexpected error: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Process PDF file and generate flashcards with custom parameters.
     * 
     * @param file The PDF file to process
     * @param maxCards Maximum number of flashcards to generate
     * @return ResponseEntity containing generated flashcards
     */
    @PostMapping("/process-pdf-advanced")
    public ResponseEntity<FlashcardResponse> processPdfAdvanced(
            @RequestParam("pdf") MultipartFile file,
            @RequestParam(value = "maxCards", defaultValue = "20") int maxCards) {
        
        logger.info("Received advanced PDF processing request for file: {} with max {} cards", 
                   file.getOriginalFilename(), maxCards);
        
        try {
            // Validate maxCards parameter
            if (maxCards <= 0 || maxCards > 100) {
                FlashcardResponse errorResponse = new FlashcardResponse(
                    null, 0L, "error", "maxCards must be between 1 and 100"
                );
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            FlashcardResponse response = flashcardService.processPdfAndGenerateFlashcards(file, maxCards);
            
            if ("success".equals(response.getStatus())) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IOException e) {
            logger.error("IO error processing PDF: {}", file.getOriginalFilename(), e);
            FlashcardResponse errorResponse = new FlashcardResponse(
                null, 0L, "error", "IO error: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request: {}", e.getMessage());
            FlashcardResponse errorResponse = new FlashcardResponse(
                null, 0L, "error", "Invalid request: " + e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            logger.error("Unexpected error processing PDF: {}", file.getOriginalFilename(), e);
            FlashcardResponse errorResponse = new FlashcardResponse(
                null, 0L, "error", "Unexpected error: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Health check endpoint.
     * 
     * @return ResponseEntity with service status
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.debug("Health check requested");
        
        boolean isReady = flashcardService.isServiceReady();
        String stats = flashcardService.getProcessingStats();
        
        String response = String.format(
            "Service Status: %s\nProcessing Stats: %s", 
            isReady ? "READY" : "NOT_READY", stats
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get service information.
     * 
     * @return ResponseEntity with service information
     */
    @GetMapping("/info")
    public ResponseEntity<Object> getServiceInfo() {
        logger.debug("Service info requested");
        
        return ResponseEntity.ok(new Object() {
            public final String service = "PDF Flashcards Backend";
            public final String version = "1.0.0";
            public final String status = flashcardService.isServiceReady() ? "READY" : "NOT_READY";
            public final String stats = flashcardService.getProcessingStats();
        });
    }
} 