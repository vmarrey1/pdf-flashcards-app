import React, { useState, useRef } from 'react';
import './FileUpload.css';

const FileUpload = ({ onFileUpload, isLoading, error }) => {
  const [isDragOver, setIsDragOver] = useState(false);
  const [selectedFile, setSelectedFile] = useState(null);
  const fileInputRef = useRef(null);

  const handleDragOver = (e) => {
    e.preventDefault();
    setIsDragOver(true);
  };

  const handleDragLeave = (e) => {
    e.preventDefault();
    setIsDragOver(false);
  };

  const handleDrop = (e) => {
    e.preventDefault();
    setIsDragOver(false);
    
    const files = e.dataTransfer.files;
    if (files.length > 0) {
      const file = files[0];
      if (file.type === 'application/pdf') {
        setSelectedFile(file);
      }
    }
  };

  const handleFileSelect = (e) => {
    const file = e.target.files[0];
    if (file) {
      setSelectedFile(file);
    }
  };

  const handleSubmit = () => {
    if (selectedFile) {
      onFileUpload(selectedFile);
    }
  };

  const handleReset = () => {
    setSelectedFile(null);
    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

  return (
    <div className="file-upload-container fellow-upload">
      <div className="upload-card fellow-card">
        <div className="upload-icon fellow-upload-icon">📄</div>
        <h2 className="upload-title fellow-title">Upload Your PDF</h2>
        <p className="upload-description fellow-description">
          Drag and drop your PDF file here, or click to browse
        </p>
        <div
          className={`upload-area fellow-upload-area ${isDragOver ? 'drag-over' : ''} ${selectedFile ? 'has-file' : ''}`}
          onDragOver={handleDragOver}
          onDragLeave={handleDragLeave}
          onDrop={handleDrop}
          onClick={() => fileInputRef.current?.click()}
        >
          <input
            ref={fileInputRef}
            type="file"
            accept=".pdf"
            onChange={handleFileSelect}
            className="file-input"
          />
          {selectedFile ? (
            <div className="selected-file fellow-selected-file">
              <span className="file-icon">📎</span>
              <span className="file-name fellow-file-name">{selectedFile.name}</span>
              <span className="file-size fellow-file-size">
                ({(selectedFile.size / 1024 / 1024).toFixed(2)} MB)
              </span>
            </div>
          ) : (
            <div className="upload-placeholder fellow-upload-placeholder">
              <span className="upload-icon-large">📁</span>
              <p>Choose a PDF file or drag it here</p>
            </div>
          )}
        </div>
        {error && (
          <div className="error-message fellow-error-message">
            <span className="error-icon">⚠️</span>
            {error}
          </div>
        )}
        <div className="upload-actions fellow-upload-actions">
          {selectedFile && (
            <>
              <button
                className="btn btn-primary fellow-btn-primary"
                onClick={handleSubmit}
                disabled={isLoading}
              >
                {isLoading ? (
                  <>
                    <span className="loading-spinner"></span>
                    Processing...
                  </>
                ) : (
                  'Generate Flashcards'
                )}
              </button>
              <button
                className="btn btn-secondary fellow-btn-secondary"
                onClick={handleReset}
                disabled={isLoading}
              >
                Choose Different File
              </button>
            </>
          )}
        </div>
        <div className="upload-tips fellow-upload-tips">
          <h3>Tips for best results:</h3>
          <ul>
            <li>Use PDFs with clear, readable text</li>
            <li>Ensure the content is well-structured</li>
            <li>Keep file size under 10MB for faster processing</li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default FileUpload; 