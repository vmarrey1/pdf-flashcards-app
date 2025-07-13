package com.pdfflashcards.backend.service;

import com.pdfflashcards.backend.model.Flashcard;
import com.pdfflashcards.backend.model.FlashcardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Main service for orchestrating PDF processing and flashcard generation.
 */
@Service
public class FlashcardService {
    
    private static final Logger logger = LoggerFactory.getLogger(FlashcardService.class);
    
    private static final int DEFAULT_MAX_CARDS = 20;
    private static final int MAX_FILE_SIZE_MB = 10;
    
    @Autowired
    private PdfProcessingService pdfProcessingService;
    
    @Autowired
    private TensorFlowService tensorFlowService;
    
    /**
     * Process PDF file and generate flashcards.
     * 
     * @param file The PDF file to process
     * @return FlashcardResponse containing generated flashcards
     * @throws IOException if file processing fails
     */
    public FlashcardResponse processPdfAndGenerateFlashcards(MultipartFile file) throws IOException {
        long startTime = System.currentTimeMillis();
        
        logger.info("Starting PDF processing for file: {}", file.getOriginalFilename());
        
        try {
            // Validate file
            validateFile(file);
            
            // Extract text from PDF
            List<String> pageTexts = pdfProcessingService.extractTextFromPdf(file);
            
            // Generate flashcards using TensorFlow
            List<Flashcard> flashcards = tensorFlowService.generateFlashcardsFromPages(
                pageTexts, DEFAULT_MAX_CARDS);
            
            long processingTime = System.currentTimeMillis() - startTime;
            
            FlashcardResponse response = new FlashcardResponse(
                flashcards, processingTime, "success", 
                String.format("Successfully generated %d flashcards", flashcards.size())
            );
            
            logger.info("PDF processing completed in {}ms. Generated {} flashcards", 
                       processingTime, flashcards.size());
            
            return response;
            
        } catch (Exception e) {
            long processingTime = System.currentTimeMillis() - startTime;
            logger.error("PDF processing failed after {}ms", processingTime, e);
            
            return new FlashcardResponse(
                null, processingTime, "error", 
                "Failed to process PDF: " + e.getMessage()
            );
        }
    }
    
    /**
     * Process PDF file and generate flashcards with custom parameters.
     * 
     * @param file The PDF file to process
     * @param maxCards Maximum number of flashcards to generate
     * @return FlashcardResponse containing generated flashcards
     * @throws IOException if file processing fails
     */
    public FlashcardResponse processPdfAndGenerateFlashcards(MultipartFile file, int maxCards) 
            throws IOException {
        long startTime = System.currentTimeMillis();
        
        logger.info("Starting PDF processing for file: {} with max {} cards", 
                   file.getOriginalFilename(), maxCards);
        
        try {
            // Validate file
            validateFile(file);
            
            // Extract text from PDF
            List<String> pageTexts = pdfProcessingService.extractTextFromPdf(file);
            
            // Generate flashcards using TensorFlow
            List<Flashcard> flashcards = tensorFlowService.generateFlashcardsFromPages(
                pageTexts, maxCards);
            
            long processingTime = System.currentTimeMillis() - startTime;
            
            FlashcardResponse response = new FlashcardResponse(
                flashcards, processingTime, "success", 
                String.format("Successfully generated %d flashcards", flashcards.size())
            );
            
            logger.info("PDF processing completed in {}ms. Generated {} flashcards", 
                       processingTime, flashcards.size());
            
            return response;
            
        } catch (Exception e) {
            long processingTime = System.currentTimeMillis() - startTime;
            logger.error("PDF processing failed after {}ms", processingTime, e);
            
            return new FlashcardResponse(
                null, processingTime, "error", 
                "Failed to process PDF: " + e.getMessage()
            );
        }
    }
    
    /**
     * Validate uploaded file.
     * 
     * @param file The file to validate
     * @throws IllegalArgumentException if file is invalid
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null");
        }
        
        // Check file size
        long fileSizeMB = file.getSize() / (1024 * 1024);
        if (fileSizeMB > MAX_FILE_SIZE_MB) {
            throw new IllegalArgumentException(
                String.format("File size (%d MB) exceeds maximum allowed size (%d MB)", 
                            fileSizeMB, MAX_FILE_SIZE_MB));
        }
        
        // Validate PDF format
        if (!pdfProcessingService.isValidPdf(file)) {
            throw new IllegalArgumentException("Invalid PDF file format");
        }
        
        logger.info("File validation passed: {} ({} MB)", 
                   file.getOriginalFilename(), fileSizeMB);
    }
    
    /**
     * Get processing statistics.
     * 
     * @return Processing statistics as a string
     */
    public String getProcessingStats() {
        return String.format("Max file size: %d MB, Default max cards: %d", 
                           MAX_FILE_SIZE_MB, DEFAULT_MAX_CARDS);
    }
    
    /**
     * Check if the service is ready to process files.
     * 
     * @return true if ready, false otherwise
     */
    public boolean isServiceReady() {
        return tensorFlowService.isModelReady();
    }
} 