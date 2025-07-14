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
    }
  };

  const handlePrevious = () => {
    if (currentCardIndex > 0) {
      setCurrentCardIndex(currentCardIndex - 1);
      setIsFlipped(false);
    }
  };

  const handleFlip = () => {
    setIsFlipped(!isFlipped);
  };

  const handleReset = () => {
    setCurrentCardIndex(0);
    setIsFlipped(false);
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
    <div className="flashcard-container fellow-flashcard-container">
      <div className="deck-header fellow-deck-header">
        <h2 className="fellow-deck-title">Your Flashcards</h2>
        <div className="deck-info fellow-deck-info">
          <span className="card-counter fellow-card-counter">
            Card {currentCardIndex + 1} of {flashcards.length}
          </span>
          <button className="btn btn-secondary fellow-btn-secondary" onClick={handleReset}>
            Upload New PDF
          </button>
        </div>
      </div>
      <div className="flashcard-wrapper fellow-flashcard-wrapper">
        <div 
          className={`flashcard fellow-flashcard ${isFlipped ? 'flipped' : ''}`}
          onClick={handleFlip}
        >
          <div className="flashcard-inner fellow-flashcard-inner">
            <div className="flashcard-front fellow-flashcard-front">
              <div className="card-content fellow-card-content">
                <h3 className="card-question fellow-card-question">{currentCard.question}</h3>
                <p className="card-hint fellow-card-hint">Click to flip</p>
              </div>
            </div>
            <div className="flashcard-back fellow-flashcard-back">
              <div className="card-content fellow-card-content">
                <h3 className="card-answer fellow-card-answer">{currentCard.answer}</h3>
                {currentCard.explanation && (
                  <div className="card-explanation fellow-card-explanation">
                    <h4>Explanation:</h4>
                    <p>{currentCard.explanation}</p>
                  </div>
                )}
                <p className="card-hint fellow-card-hint">Click to flip back</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="flashcard-controls fellow-flashcard-controls">
        <button
          className="btn btn-secondary fellow-btn-secondary"
          onClick={handlePrevious}
          disabled={currentCardIndex === 0}
        >
          ← Previous
        </button>
        <button
          className="btn btn-primary fellow-btn-primary"
          onClick={handleFlip}
        >
          {isFlipped ? 'Show Question' : 'Show Answer'}
        </button>
        <button
          className="btn btn-secondary fellow-btn-secondary"
          onClick={handleNext}
          disabled={currentCardIndex === flashcards.length - 1}
        >
          Next →
        </button>
      </div>
      <div className="deck-progress fellow-deck-progress">
        <div className="progress-bar fellow-progress-bar">
          <div 
            className="progress-fill fellow-progress-fill"
            style={{ width: `${((currentCardIndex + 1) / flashcards.length) * 100}%` }}
          ></div>
        </div>
        <span className="progress-text fellow-progress-text">
          {Math.round(((currentCardIndex + 1) / flashcards.length) * 100)}% Complete
        </span>
      </div>
    </div>
  );
};

export default FlashcardDeck; 