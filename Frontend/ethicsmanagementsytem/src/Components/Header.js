import React from 'react';
import { Link } from 'react-router-dom';
import '../Styling/Header.css'



const Header = ({ isLoggedIn, username, onLogout }) => {
    return (
        <header className="main-header">
            <nav>
                <ul className="nav-links">
                    <li><Link to="/Home">Home</Link></li>
                    <li><Link to="/about">About</Link></li>
                    <li><Link to="/Profile">Profile</Link></li>
                    <li><Link to="/Submission">Submission</Link></li>
                    <li><Link to="/Analytics">Analytics</Link></li>
                    {isLoggedIn ? (
                        <div className="user-section">
                            <li className="user-info">
                                Welcome, {username}
                            </li>
                            <li>
                                <button onClick={onLogout} className="logout-button">Logout</button>
                            </li>
                        </div>
                    ) : (
                        <li><Link to="/Login" className="register-link">Login Now</Link></li>
                    )}
                </ul>
            </nav>
        </header>
    );
};


export default Header;
