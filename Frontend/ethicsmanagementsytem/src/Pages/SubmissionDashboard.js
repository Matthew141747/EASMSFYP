import React, { useState, useEffect, useCallback  } from 'react';
import '../Styling/SubmissionDashboard.css'
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

function SubmissionDashboard() {
    const [submissions, setSubmissions] = useState([]);
    const [selectedSubmission, setSelectedSubmission] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');

    //state variables for pagination and filtering
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0); // State to store total pages

    const [totalSubmissions, setTotalSubmissions] = useState(null);
    const [itemsPerPage, setItemsPerPage] = useState(10); // Default items per page
    const [facultyFilter, setFacultyFilter] = useState('');
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);




    const fetchSubmissions = useCallback(async () => {
        let queryParams = `?page=${currentPage - 1}&size=${itemsPerPage}`;
        if (facultyFilter) queryParams += `&faculty=${facultyFilter}`;
        if (startDate) queryParams += `&startDate=${startDate.toISOString().split('T')[0]}`;
        if (endDate) queryParams += `&endDate=${endDate.toISOString().split('T')[0]}`;
        
        const token = localStorage.getItem('userToken');
        try {
            const response = await fetch(`http://localhost:8080/api/submissions/All${queryParams}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
            });
    
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
    
            const data = await response.json();
            setSubmissions(data.content);
            setTotalPages(data.totalPages);
            setTotalSubmissions(data.totalElements);
        } catch (error) {
            console.error('Failed to fetch submissions:', error);
            // Handle the error state here
        }
    }, [currentPage, itemsPerPage, facultyFilter, startDate, endDate]);
  
    useEffect(() => {
        fetchSubmissions();
    }, [fetchSubmissions]);

    
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
        if (!dateString) return '';
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
    };

    const handleItemsPerPageChange = (event) => {
        const newItemsPerPage = Number(event.target.value);
        setItemsPerPage(newItemsPerPage);
        setCurrentPage(1); // Reset to the first page, index starts at 0
        // You may need to call fetchSubmissions here to reload the data
        //fetchSubmissions(); // You might need to modify fetchSubmissions to not use useCallback or to properly handle the change in items per page
    };

      // Adjust the button handlers to handle the user-friendly pagination
      const handlePreviousClick = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    const handleNextClick = () => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    };

    return (
        <div className="committee-dashboard">
            <div className="search-and-filter">
                <input
                    type="text"
                    className="search-bar"
                    placeholder="Search submissions..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
                <div className="filters">
                    <select value={facultyFilter} onChange={e => setFacultyFilter(e.target.value)}>
                        <option value="">Select Faculty</option>
                        <option value="arts_humanities_social_sciences">Faculty of Arts, Humanities and Social Sciences</option>
                        <option value="education_health_sciences">Faculty of Education and Health Sciences</option>
                        <option value="kemmy_business_school">Kemmy Business School</option>
                        <option value="science_engineering">Faculty of Science and Engineering</option>
                        <option value="irish_world_academy">Irish World Academy of Music & Dance</option>
                    </select>
                    <DatePicker
                        selected={startDate}
                        onChange={date => setStartDate(date)}
                        className="date-picker"
                        placeholderText="Start Date"
                    />
                    <DatePicker
                        selected={endDate}
                        onChange={date => setEndDate(date)}
                        className="date-picker"
                        placeholderText="End Date"
                    />
                    <button onClick={fetchSubmissions} className="fetch-button">Show</button>
                </div>
            </div>

            <div className="dashboard-main">


                <div className="submissions-table-container">
                    
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

                </div>


                <div className="pagination-controls">
                <div className="pagination-left">
                    <button onClick={handlePreviousClick} disabled={currentPage === 1}>
                        Previous
                    </button>
                    {/* Display the current page and total pages */}
                    <span>Page {currentPage} of {totalPages}</span>
                    <button onClick={handleNextClick} disabled={currentPage === totalPages}>
                        Next
                    </button>
                    <select value={itemsPerPage} onChange={handleItemsPerPageChange}>
                        {[5, 10, 20, 50].map(size => (
                            <option key={size} value={size}>{size} per page</option>
                        ))}
                    </select>
                </div>
                <div className="pagination-right">
                    <span>Total Submissions: {totalSubmissions}</span>
                </div>
            </div>

            </div>

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
    );
}

export default SubmissionDashboard;