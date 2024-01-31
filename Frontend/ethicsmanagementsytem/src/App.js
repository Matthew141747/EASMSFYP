import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import React, { useState } from 'react';


import Header from './Components/Header';
import Home from './Pages/Home'
import About from './Pages/About'
import Profile from './Pages/Profile'
import Submission from './Pages/Submission'
import Analytics from './Pages/Analytics'
import Registration from './Pages/Registration'
import LoginForm from './Pages/Login';



export function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');

    const handleLogout = () => {
          // Clear token and reset state
          localStorage.removeItem('userToken');
          setIsLoggedIn(false);
          setUsername('');
      };

      const handleLogin = (username) => {
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
                    <Route path="/analytics" element = {<Analytics/>} />
                    <Route path="/registration" element = {<Registration/>} />
                    <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />

                </Routes>
            </BrowserRouter>
        </div>
  );
}


export default App;
