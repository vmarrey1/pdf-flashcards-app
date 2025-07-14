import React, { useState, useContext } from 'react';
import './App.css';
import FileUpload from './components/FileUpload';
import FlashcardDeck from './components/FlashcardDeck';
import Header from './components/Header';
import AuthModal from './components/AuthModal';
import { AuthContext } from './components/AuthContext';

function App() {
  const { user, token, logout } = useContext(AuthContext);
  const [flashcards, setFlashcards] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [showAuthModal, setShowAuthModal] = useState(false);
  const [history, setHistory] = useState([]);
  const [showHistory, setShowHistory] = useState(false);

  const handleFileUpload = async (file) => {
    setIsLoading(true);
    setError(null);
    try {
      const formData = new FormData();
      formData.append('pdf', file);
      const response = await fetch('/api/process-pdf', {
        method: 'POST',
        body: formData,
        headers: token ? { Authorization: `Bearer ${token}` } : {},
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

  const fetchHistory = async () => {
    setShowHistory(true);
    setIsLoading(true);
    setError(null);
    try {
      const response = await fetch('/api/flashcards/history', {
        headers: token ? { Authorization: `Bearer ${token}` } : {},
      });
      if (!response.ok) {
        throw new Error('Failed to fetch history');
      }
      const data = await response.json();
      setHistory(data || []);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="App">
      <Header>
        <div className="auth-header-actions">
          {user ? (
            <>
              <button className="btn btn-secondary" onClick={fetchHistory}>
                My Flashcards
              </button>
              <button className="btn btn-secondary" onClick={logout}>
                Logout
              </button>
            </>
          ) : (
            <button className="btn btn-primary" onClick={() => setShowAuthModal(true)}>
              Login / Register
            </button>
          )}
        </div>
      </Header>
      <main className="main-content">
        <AuthModal isOpen={showAuthModal} onClose={() => setShowAuthModal(false)} />
        {showHistory && user ? (
          <div className="history-section">
            <h2>My Flashcard History</h2>
            {isLoading ? (
              <div>Loading...</div>
            ) : history.length === 0 ? (
              <div>No flashcard history found.</div>
            ) : (
              <ul className="history-list">
                {history.map((h, idx) => (
                  <li key={idx} className="history-item">
                    <div className="history-title">{h.title || `Session ${idx + 1}`}</div>
                    <div className="history-date">{new Date(h.createdAt).toLocaleString()}</div>
                    <button className="btn btn-primary" onClick={() => { setFlashcards(h.flashcards); setShowHistory(false); }}>
                      View Flashcards
                    </button>
                  </li>
                ))}
              </ul>
            )}
            <button className="btn btn-secondary" onClick={() => setShowHistory(false)}>
              Back
            </button>
          </div>
        ) : flashcards.length === 0 ? (
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
