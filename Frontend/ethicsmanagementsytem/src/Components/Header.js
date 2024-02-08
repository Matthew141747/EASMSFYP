import React from 'react';
import { Link } from 'react-router-dom';
import '../Styling/Header.css'


const Header = ({ isLoggedIn, username, onLogout }) => {
    return (
        <header className="main-header">
            <nav className="nav-container">
                <ul className="nav-links">
                    <li><Link to="/Home">Home</Link></li>
                    <li><Link to="/Profile">Profile</Link></li>
                    <li><Link to="/Submission">Submission</Link></li>
                    <li><Link to="/SubmissionDashboard">Submission Dashboard</Link></li>
                    <li><Link to="/Analytics">Analytics</Link></li>
                    <li><Link to="/about">About</Link></li>
                </ul>
                <h1 className="header-title">Ethics Application Portal</h1>
                <div className="user-section">
                    {isLoggedIn ? (
                        <>
                            <span className="user-info">
                                Welcome, {username}
                            </span>
                            <button onClick={onLogout} className="logout-button">Logout</button>
                        </>
                    ) : (
                        <Link to="/Login" className="register-link">Login Now</Link>
                    )}
                </div>
            </nav>
        </header>
    );
};


export default Header;
