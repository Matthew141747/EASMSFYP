import '../Styling/Register.css';
import React, { useState } from 'react';

const RegisterForm = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordB, setPasswordB] = useState('');


    const handleSubmit = async (event) => {
        event.preventDefault();

        const userData = {
            username: username,
            email: email,
            password: password,
        };

        try {
            
            const response = await fetch("http://localhost:8080/api/users/register", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData),
            });

            if (response.ok) {
               
            } else {
                const responseData = await response.json();

                console.log("error", response.status, responseData);
            }
        } catch (error) {
            // Handle network errors or other issues
            console.error('Registration failed:', error);
        }
    };

    return (
        <div className="register-form-container">
            <div className="registration-box">
                <form onSubmit={handleSubmit}>
                    <div>
                        <label>Username:</label>
                        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                    </div>
                    <div>
                        <label>Email:</label>
                        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <div>
                        <label>Password:</label>
                        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <div>
                        <label>Confirm Password:</label>
                        <input type="password" value={passwordB} onChange={(e) => setPasswordB(e.target.value)} />
                    </div>

                    <div>
                    <button type="submit">Register</button>
                    </div>
                    
                </form>

            </div>
        </div>
    );
};

export default RegisterForm;