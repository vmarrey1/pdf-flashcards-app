import React, { useState } from 'react';
import './App.css';
import FileUpload from './components/FileUpload';
import FlashcardDeck from './components/FlashcardDeck';
import Header from './components/Header';

function App() {
  const [flashcards, setFlashcards] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleFileUpload = async (file) => {
    setIsLoading(true);
    setError(null);
    
    try {
      const formData = new FormData();
      formData.append('pdf', file);

      // TODO: Replace with your actual backend endpoint
      const response = await fetch('/api/process-pdf', {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        throw new Error('Failed to process PDF');
      }

      const data = await response.json();
      setFlashcards(data.flashcards || []);
    } catch (err) {
      setError(err.message);
      console.error('Error processing PDF:', err);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="App">
      <Header />
      <main className="main-content">
        {flashcards.length === 0 ? (
          <FileUpload 
            onFileUpload={handleFileUpload}
            isLoading={isLoading}
            error={error}
          />
        ) : (
          <FlashcardDeck 
            flashcards={flashcards}
            onReset={() => setFlashcards([])}
          />
        )}
      </main>
    </div>
  );
}

export default App;
