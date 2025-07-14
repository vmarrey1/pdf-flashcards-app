import React, { useState, useContext } from 'react';
import { AuthContext } from './AuthContext';
import './AuthModal.css';

const API_URL = '/api/auth';

const AuthModal = ({ isOpen, onClose }) => {
  const { login } = useContext(AuthContext);
  const [mode, setMode] = useState('login');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const res = await fetch(`${API_URL}/${mode}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });
      const data = await res.json();
      if (res.ok && data.token) {
        login(data.token, { username });
        onClose();
      } else {
        setError(data || 'Authentication failed');
      }
    } catch (err) {
      setError('Network error');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-modal-backdrop fellow-auth-modal-backdrop">
      <div className="auth-modal fellow-auth-modal">
        <button className="auth-modal-close fellow-auth-modal-close" onClick={onClose}>&times;</button>
        <h2 className="fellow-auth-title">{mode === 'login' ? 'Login' : 'Register'}</h2>
        <form className="fellow-auth-form" onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={e => setUsername(e.target.value)}
            required
            autoFocus
            className="fellow-auth-input"
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
            className="fellow-auth-input"
          />
          {error && <div className="auth-error fellow-auth-error">{typeof error === 'string' ? error : JSON.stringify(error)}</div>}
          <button type="submit" className="btn btn-primary fellow-btn-primary" disabled={loading}>
            {loading ? 'Please wait...' : (mode === 'login' ? 'Login' : 'Register')}
          </button>
        </form>
        <div className="auth-switch fellow-auth-switch">
          {mode === 'login' ? (
            <>
              New user?{' '}
              <button type="button" className="fellow-auth-switch-btn" onClick={() => setMode('register')}>Register</button>
            </>
          ) : (
            <>
              Already have an account?{' '}
              <button type="button" className="fellow-auth-switch-btn" onClick={() => setMode('login')}>Login</button>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default AuthModal; 