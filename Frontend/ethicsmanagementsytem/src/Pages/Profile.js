import React, { useState, useEffect } from 'react';
import '../Styling/Profile.css'; 
import emailIcon from '../Components/Assets/email.png'; 
import userIcon from '../Components/Assets/person.png'; 
import placeholderProfilePic from '../Components/Assets/profilepic.png';

const Profile = () => {
    const [userDetails, setUserDetails] = useState(null);
    const [submissions, setSubmissions] = useState([]);

    const [currentPage, setCurrentPage] = useState(1);
    const submissionsPerPage = 2; 

    const indexOfLastSubmission = currentPage * submissionsPerPage;
    const indexOfFirstSubmission = indexOfLastSubmission - submissionsPerPage;
    const currentSubmissions = submissions.slice(indexOfFirstSubmission, indexOfLastSubmission);

    const totalSubmissions = submissions.length;
    const totalPages = Math.ceil(totalSubmissions / submissionsPerPage);

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

        const fetchUserSubmissions = async () => {
            const token = localStorage.getItem('userToken');
            try {
                const response = await fetch('http://localhost:8080/api/submissions/userSubmissions', {
                    method: "GET",
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                if(response.ok) {
                    const submissions = await response.json();
                    setSubmissions(submissions);
                    console.log(submissions);

                    // Update state to hold these submissions
                } else {
                    console.log('Error fetching submissions');
                }
            } catch (error) {
                console.error('Error fetching submissions', error);
            }
        };

        fetchUserSubmissions();
        fetchUserProfile();
    }, []);

    if (!userDetails) {
        return <div>Loading...</div>;
    }

    const handleEdit = (field) => {
        //console.log(`Edit ${field}`);
    };

    return (
        <div className="profile-page">
        <div className="profile-container">
            <div className="user-details-section">
            <h2>{userDetails.username}'s Profile</h2>
                    <div className="profile-pic-container">
                        <img src={placeholderProfilePic} alt="Profile" className="profile-pic" />
                    </div>
                    <div className="detail-with-icon">
                        <img src={userIcon} alt="User" className="icon" />
                        <p>Student ID: {userDetails.studentId}</p>
                    </div>
                    <div className="detail-with-icon">
                        <img src={emailIcon} alt="Email" className="icon" />
                        <p>Email: {userDetails.email}</p>
                    </div>
                    <p>Account Type: {userDetails.accountType}</p>
                    <p>Faculty: {userDetails.faculty}</p>
                    <p>Department: {userDetails.department}</p>
                    <button onClick={() => handleEdit('details')} className="edit-details-btn">Edit Details</button>
                </div>

                <div className="submissions-section">
                <h3>Your Submissions</h3>
                <div className="submission-info">
                    {currentSubmissions.length > 0 ? (
                        currentSubmissions.map((submission) => (
                            <div className="submission-card" key={submission.submissionId}>
                                <h4 className="submission-title">{submission.applicantName}'s Submission</h4>
                                <p className="submission-faculty"><strong>Faculty:</strong> {submission.faculty}</p>
                                <p className="submission-department"><strong>Department:</strong> {submission.department || "Not specified"}</p>
                                <p className="submission-date"><strong>Submitted on:</strong> {submission.submissionDate}</p>
                                <p className="submission-files"><strong>Files:</strong> {submission.fileMetadataList?.map(file => file.fileName).join(', ')}</p>
                                <p className="submission-review-status"><strong>Status:</strong> <span className="review-status">{submission.reviewStatus}</span></p>
                                <p className="submission-supervisor"><strong>Supervisor:</strong> {submission.supervisorName}</p>
                            </div>
                        ))
                    ) : (
                        <p className="no-submissions">No submissions made yet.</p>
                    )}
                </div>
                {currentSubmissions.length > 0 && (
                    <div className="pagination-controls">
                        <button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 1}>
                            Previous
                        </button>
                        <span>Page {currentPage} of {totalPages} | Total submissions: {totalSubmissions}</span>
                        <button onClick={() => setCurrentPage(currentPage + 1)} disabled={currentPage >= totalPages}>
                            Next
                        </button>
                    </div>
                )}
            </div>
        </div>

        </div>

    );
}

export default Profile;