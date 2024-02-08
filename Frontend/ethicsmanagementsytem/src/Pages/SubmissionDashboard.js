import React, { useState, useEffect } from 'react';
import '../Styling/SubmissionDashboard.css'

function SubmissionDashboard() {
    const [submissions, setSubmissions] = useState([]);
    const [selectedSubmission, setSelectedSubmission] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const fetchSubmissions = async () => {
            const token = localStorage.getItem('userToken'); // or however you manage authentication
            const response = await fetch('http://localhost:8080/api/submissions/All', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
            });
            
            if (response.ok) {
                const data = await response.json();
                setSubmissions(data); // Assuming the backend returns an array of submissions
            } else {
                // Handle errors here, such as showing a notification to the user
                console.error('Failed to fetch submissions');
            }
        };

        fetchSubmissions();
    }, []); // The empty array means this effect will only run once when the component mounts

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
        // TODO: Implement search functionality
    };

    const selectSubmission = (submission) => {
        setSelectedSubmission(submission);
        // TODO: Display the details of the selected submission
    };

    const downloadFiles = (submissionId) => {
        // Find the submission by ID
        const submission = submissions.find(sub => sub.submissionId === submissionId);
        if (!submission) {
            console.error('Submission not found');
            return;
        }
    
        // Iterate over the fileMetadataList and download each file
        submission.fileMetadataList.forEach(file => {
            const correctEndpoint = file.filePath.replace('s3.us-west-2.amazonaws.com', 's3-eu-west-1.amazonaws.com');
            
            fetch(correctEndpoint, {
                method: 'GET',
                // Additional headers if needed
            })
            .then(response => response.blob())
            .then(blob => {
                // Create a new URL for the blob
                const blobUrl = URL.createObjectURL(blob);
    
                // Create an anchor element and trigger download
                const link = document.createElement('a');
                link.href = blobUrl;
                link.download = file.fileName; // Set the default filename for the download
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
    
                // Clean up the URL object
                URL.revokeObjectURL(blobUrl);
            })
            .catch(error => console.error('Error downloading file:', error));
        });
    };
    
    const formatDate = (dateString) => {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
      };

    return (
        <div className="committee-dashboard">
            <input
                type="text"
                className="search-bar"
                placeholder="Search submissions..."
                value={searchTerm}
                onChange={handleSearchChange}
            />

            <div className="dashboard-content">
                <table className="submissions-table">
                    <thead>
                        <tr>
                            <th>Student ID</th>
                            <th>Faculty</th>
                            <th>Department</th>
                            <th>Submission Date</th>
                            <th>Review Status</th>
                            <th>Number of Files</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    {submissions.map((submission) => (
                        <tr key={submission.submissionId}>
                        <td>{submission.studentId}</td>
                        <td>{submission.faculty}</td>
                        <td>{submission.department}</td>
                        <td>{formatDate(submission.submissionDate)}</td>                        
                        <td>{submission.reviewStatus}</td> {/* Replace with actual status */}
                        <td>{submission.fileMetadataList?.length || 0}</td> {/* Use optional chaining */}
                        <td>
                            <button onClick={() => selectSubmission(submission)}>
                            View Details
                            </button>
                        </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                {selectedSubmission && (
                    <div className="submission-details">
                        <p>Student ID: {selectedSubmission.studentId}</p>
                        <p>Faculty: {selectedSubmission.faculty}</p>
                        <p>Department: {selectedSubmission.department}</p>
                        <p>Number of Files: {selectedSubmission.fileMetadataList?.length || 0}</p>
                        <ul>
                            {selectedSubmission.fileMetadataList?.map(file => (
                                <li key={file.id}>
                                    {file.fileName} - {file.fileType}
                                </li>
                            ))}
                        </ul>
                        <button onClick={() => downloadFiles(selectedSubmission.submissionId)}>
                            Download Files
                        </button>
                    </div>
                )}
            </div>
        </div>
    );
}

export default SubmissionDashboard;