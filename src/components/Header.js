import React from 'react';
import './Header.css';

const Header = ({ children }) => {
  return (
    <header className="header">
      <div className="header-content">
        <h1 className="app-title">
          <span className="title-icon">📚</span>
          PDF Flashcards
        </h1>
        <p className="app-subtitle">
          Transform your PDFs into interactive flashcards
        </p>
        {children && <div className="header-actions">{children}</div>}
      </div>
    </header>
  );
};

export default Header; 