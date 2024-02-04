import React, { useState, useEffect } from 'react';
import '../Styling/Profile.css'; 

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

    return (
        <div className="profile-container">
            <div className="profile-card">
                <h2>{userDetails.username}'s Profile</h2>
                <p>Email: {userDetails.email}</p>
                <p>Account Type: {userDetails.accountType}</p>
                {/* Include other user details */}
            </div>
        </div>
    );
};

export default Profile;