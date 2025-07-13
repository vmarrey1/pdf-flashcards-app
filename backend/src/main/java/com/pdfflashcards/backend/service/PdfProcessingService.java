package com.pdfflashcards.backend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for processing PDF files and extracting text content.
 */
@Service
public class PdfProcessingService {
    
    private static final Logger logger = LoggerFactory.getLogger(PdfProcessingService.class);
    
    /**
     * Extract text content from a PDF file.
     * 
     * @param file The PDF file to process
     * @return List of text content by page
     * @throws IOException if PDF processing fails
     */
    public List<String> extractTextFromPdf(MultipartFile file) throws IOException {
        logger.info("Processing PDF file: {}", file.getOriginalFilename());
        
        List<String> pageTexts = new ArrayList<>();
        
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper textStripper = new PDFTextStripper();
            
            int totalPages = document.getNumberOfPages();
            logger.info("PDF has {} pages", totalPages);
            
            // Extract text from each page
            for (int pageNum = 1; pageNum <= totalPages; pageNum++) {
                textStripper.setStartPage(pageNum);
                textStripper.setEndPage(pageNum);
                
                String pageText = textStripper.getText(document);
                pageTexts.add(pageText);
                
                logger.debug("Extracted {} characters from page {}", pageText.length(), pageNum);
            }
        }
        
        logger.info("Successfully extracted text from {} pages", pageTexts.size());
        return pageTexts;
    }
    
    /**
     * Extract text content from a PDF file as a single string.
     * 
     * @param file The PDF file to process
     * @return Combined text content from all pages
     * @throws IOException if PDF processing fails
     */
    public String extractFullTextFromPdf(MultipartFile file) throws IOException {
        logger.info("Processing PDF file for full text extraction: {}", file.getOriginalFilename());
        
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper textStripper = new PDFTextStripper();
            String fullText = textStripper.getText(document);
            
            logger.info("Extracted {} characters from PDF", fullText.length());
            return fullText;
        }
    }
    
    /**
     * Validate if the uploaded file is a valid PDF.
     * 
     * @param file The file to validate
     * @return true if valid PDF, false otherwise
     */
    public boolean isValidPdf(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        
        // Check content type
        if (contentType == null || !contentType.equals("application/pdf")) {
            logger.warn("Invalid content type: {} for file: {}", contentType, fileName);
            return false;
        }
        
        // Check file extension
        if (fileName == null || !fileName.toLowerCase().endsWith(".pdf")) {
            logger.warn("Invalid file extension for file: {}", fileName);
            return false;
        }
        
        // Try to load the PDF to verify it's valid
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            int pages = document.getNumberOfPages();
            logger.info("Valid PDF with {} pages: {}", pages, fileName);
            return true;
        } catch (IOException e) {
            logger.warn("Failed to load PDF file: {}", fileName, e);
            return false;
        }
    }
    
    /**
     * Get PDF metadata including page count and file size.
     * 
     * @param file The PDF file
     * @return PDF metadata as a string
     * @throws IOException if PDF processing fails
     */
    public String getPdfMetadata(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            int pageCount = document.getNumberOfPages();
            long fileSize = file.getSize();
            
            return String.format("Pages: %d, Size: %d bytes", pageCount, fileSize);
        }
    }
} 