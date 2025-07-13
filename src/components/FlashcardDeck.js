import React, { useState } from 'react';
import './FlashcardDeck.css';

const FlashcardDeck = ({ flashcards, onReset }) => {
  const [currentCardIndex, setCurrentCardIndex] = useState(0);
  const [isFlipped, setIsFlipped] = useState(false);
  // Removed unused showAnswer state

  const currentCard = flashcards[currentCardIndex];

  const handleNext = () => {
    if (currentCardIndex < flashcards.length - 1) {
      setCurrentCardIndex(currentCardIndex + 1);
      setIsFlipped(false);
      setShowAnswer(false);
    }
  };

  const handlePrevious = () => {
    if (currentCardIndex > 0) {
      setCurrentCardIndex(currentCardIndex - 1);
      setIsFlipped(false);
      setShowAnswer(false);
    }
  };

  const handleFlip = () => {
    setIsFlipped(!isFlipped);
  };

  // Removed unused handleShowAnswer function

  const handleReset = () => {
    setCurrentCardIndex(0);
    setIsFlipped(false);
    setShowAnswer(false);
    onReset();
  };

  if (!currentCard) {
    return (
      <div className="flashcard-container">
        <div className="no-cards">
          <h2>No flashcards available</h2>
          <button className="btn btn-primary" onClick={handleReset}>
            Upload New PDF
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="flashcard-container">
      <div className="deck-header">
        <h2>Your Flashcards</h2>
        <div className="deck-info">
          <span className="card-counter">
            Card {currentCardIndex + 1} of {flashcards.length}
          </span>
          <button className="btn btn-secondary" onClick={handleReset}>
            Upload New PDF
          </button>
        </div>
      </div>

      <div className="flashcard-wrapper">
        <div 
          className={`flashcard ${isFlipped ? 'flipped' : ''}`}
          onClick={handleFlip}
        >
          <div className="flashcard-inner">
            <div className="flashcard-front">
              <div className="card-content">
                <h3 className="card-question">{currentCard.question}</h3>
                <p className="card-hint">Click to flip</p>
              </div>
            </div>
            <div className="flashcard-back">
              <div className="card-content">
                <h3 className="card-answer">{currentCard.answer}</h3>
                {currentCard.explanation && (
                  <div className="card-explanation">
                    <h4>Explanation:</h4>
                    <p>{currentCard.explanation}</p>
                  </div>
                )}
                <p className="card-hint">Click to flip back</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="flashcard-controls">
        <button
          className="btn btn-secondary"
          onClick={handlePrevious}
          disabled={currentCardIndex === 0}
        >
          ← Previous
        </button>
        
        <button
          className="btn btn-primary"
          onClick={handleFlip}
        >
          {isFlipped ? 'Show Question' : 'Show Answer'}
        </button>
        
        <button
          className="btn btn-secondary"
          onClick={handleNext}
          disabled={currentCardIndex === flashcards.length - 1}
        >
          Next →
        </button>
      </div>

      <div className="deck-progress">
        <div className="progress-bar">
          <div 
            className="progress-fill"
            style={{ width: `${((currentCardIndex + 1) / flashcards.length) * 100}%` }}
          ></div>
        </div>
        <span className="progress-text">
          {Math.round(((currentCardIndex + 1) / flashcards.length) * 100)}% Complete
        </span>
      </div>
    </div>
  );
};

export default FlashcardDeck; 