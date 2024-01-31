import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

import '../Styling/LoginForm.css'; 

const LoginForm = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate(); // Hook for navigation


    const handleLogin = async (event) => {
        event.preventDefault();

        const userData = {
            username: username,
            password: password
        };


        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });

            if (response.ok) {
                const data = await response.json();
                // Save the token in local storage or context
                 localStorage.setItem('userToken', data.token);
                
                onLogin(username);
                navigate('/Home'); // Redirect to home page
            } else {
                
            }
        } catch (error) {
            console.error('Login failed:', error);
        }
    

    };

    return (
        <div className="login-form-container">
            <div className="login-box">
                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label>Username:</label>
                        <input type="userName" value={username} onChange={(e) => setUsername(e.target.value)} />
                    </div>
                    <div className="form-group">
                        <label>Password:</label>
                        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <div className="form-actions">
                        <button type="submit">Login</button>
                        <Link to="/Registration" className="register-link">Register</Link>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default LoginForm;