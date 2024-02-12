import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import React, { useState, useEffect } from 'react';


import Header from './Components/Header';
import Home from './Pages/Home'
import About from './Pages/About'
import Profile from './Pages/Profile'
import Submission from './Pages/Submission'
import Analytics from './Pages/Analytics'
import Registration from './Pages/Registration'
import LoginForm from './Pages/Login';
import SubmissionDashboard from './Pages/SubmissionDashboard';


export function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');


    useEffect(() => {
        // Check if user is logged in when the app component mounts
        const token = localStorage.getItem('userToken');
        const storedUsername = localStorage.getItem('username');
        if (token) {
            setIsLoggedIn(true);
            setUsername(storedUsername || '');
        }
    }, []);

    const handleLogout = () => {
          // Clear token and reset state
          localStorage.removeItem('userToken');
          localStorage.removeItem('username');
          setIsLoggedIn(false);
          setUsername('');
      };

      const handleLogin = (username, token) => {
        // Save the username and token in local storage
        localStorage.setItem('username', username);
        localStorage.setItem('userToken', token);
        setIsLoggedIn(true);
        setUsername(username);
    };
    
  return (
     <div>
            <BrowserRouter>
            <Header 
                isLoggedIn={isLoggedIn} 
                username={username} 
                onLogout={handleLogout} 
                />
                <Routes>
                    <Route path="/home" element={<Home />} />
                    <Route path="/about" element = {<About/>} />
                    <Route path="/profile" element = {<Profile/>} />
                    <Route path="/submission" element = {<Submission/>} />
                    <Route path='/submissionDashboard' element = {<SubmissionDashboard/>} />
                    <Route path="/analytics" element = {<Analytics/>} />
                    <Route path="/registration" element = {<Registration/>} />
                    <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />

                </Routes>
            </BrowserRouter>
        </div>
  );
}


export default App;
