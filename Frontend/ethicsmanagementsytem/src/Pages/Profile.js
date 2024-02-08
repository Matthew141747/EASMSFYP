import React, { useState, useEffect } from 'react';
import '../Styling/Profile.css'; 
import emailIcon from '../Components/Assets/email.png'; 
import userIcon from '../Components/Assets/person.png'; 

const Profile = () => {
    const [userDetails, setUserDetails] = useState(null);

    useEffect(() => {
        const fetchUserProfile = async () => {
            const token = localStorage.getItem('userToken');
            console.log("Sending Token: ", token);
            try {
                const response = await fetch('http://localhost:8080/api/users/profile', {
                    method: "GET",
                    headers: { 'Authorization': `Bearer ${token}` }
                });

                if(response.ok) {
                    const data = await response.json();
                    setUserDetails(data);
                } else {
                    const errorData = await response.text(); // or response.json() if the error is in JSON format
                    console.log('Error fetching profile:', errorData);
                }
            } catch (error) {
                console.error('Error', error);
            }
        };

        fetchUserProfile();
    }, []);

    if (!userDetails) {
        return <div>Loading...</div>;
    }

    const handleEdit = (field) => {
        console.log(`Edit ${field}`);
        // Implement the logic to handle editing
    };

    return (
        <div className="profile-container">
            <div className="profile-card">
                <div className="profile-detail">
                    <img src={userIcon} alt="User" className="icon" />
                    <h2>{userDetails.username}'s Profile</h2>
                </div>
                <div className="profile-detail">
                    <img src={emailIcon} alt="Email" className="icon" />
                    <p>Email: {userDetails.email}</p>
                </div>
                <p>Account Type: {userDetails.accountType}</p>
                <p>Faculty: {userDetails.faculty}</p>
                <p>Department: {userDetails.department}</p>
                {/* Other user details */}
                <button onClick={() => handleEdit('username')}>Change Username</button>
                <button onClick={() => handleEdit('password')}>Change Password</button>
                <button onClick={() => handleEdit('email')}>Change Email</button>
                {/* Buttons for other fields */}
            </div>
        </div>
    );
}

export default Profile;