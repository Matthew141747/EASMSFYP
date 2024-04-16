import React from 'react';
import { Link } from 'react-router-dom';
import '../Styling/Header.css'


const Header = ({ isLoggedIn, username, onLogout, accountType }) => {

     // Define a list of links for admin and faculty
     const adminFacultyLinks = (
        <>
            <li><Link to="/Profile">Profile</Link></li>
            <li><Link to="/Submission">Submission</Link></li>
            <li><Link to="/Analytics">Analytics</Link></li>
        </>
    );

    const adminFacultyLinksB = (
        <>
            <li><Link to="/SubmissionDashboard">Submission Dashboard</Link></li>
            <li><Link to="/SubmissionTracker">Track Submissions</Link></li>
        </>
    );

    // Define a list of links for applicants
    const applicantLinks = (
        <>
            <li><Link to="/Submission">Submission</Link></li>
            <li><Link to="/Profile">Profile</Link></li>
        </>
    );

    return (
        <header className="main-header">
        <nav className="nav-container">
            <ul className="nav-links">
                <li><Link to="/Home">Home</Link></li>
                {isLoggedIn && (accountType === 'admin' || accountType === 'faculty') && adminFacultyLinks}
                {isLoggedIn && accountType === 'applicant' && applicantLinks}
                <li><Link to="/about">About</Link></li>
            </ul>
            <h1 className="header-title">Ethics Application Portal</h1>
            {isLoggedIn && (accountType === 'admin' || accountType === 'faculty') && <div className="faculty-links-container">{adminFacultyLinksB}</div>}
            <div className="user-section">
                {isLoggedIn ? (
                    <>
                        <span className="user-info">
                             {username}
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