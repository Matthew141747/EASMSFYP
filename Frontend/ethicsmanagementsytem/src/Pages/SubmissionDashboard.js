import React, { useState, useEffect, useCallback  } from 'react';
import '../Styling/SubmissionDashboard.css'
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

function SubmissionDashboard() {
    //const accountType = localStorage.getItem('accountType'); // Retrieve the account type from localStorage

    const [submissions, setSubmissions] = useState([]);
    const [selectedSubmissions, setSelectedSubmissions] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');

    //state variables for pagination and filtering
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0); // State to store total pages

    const [totalSubmissions, setTotalSubmissions] = useState(null);
    const [itemsPerPage, setItemsPerPage] = useState(10); // Default items per page
    const [facultyFilter, setFacultyFilter] = useState('');
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);

    // Sorting Variables
    const [sortOrder, setSortOrder] = useState('recent'); 
    const [reviewStatusFilter, setReviewStatusFilter] = useState('');

    const [displayedSubmissions, setDisplayedSubmissions] = useState([]);

    const fetchSubmissions = useCallback(async (sortOrderParam = sortOrder) => {
        let queryParams = `?page=${currentPage - 1}&size=${itemsPerPage}&sort=${sortOrderParam}`;
        if (facultyFilter) queryParams += `&faculty=${facultyFilter}`;
        if (startDate) queryParams += `&startDate=${startDate.toISOString().split('T')[0]}`;
        if (endDate) queryParams += `&endDate=${endDate.toISOString().split('T')[0]}`;
        if (reviewStatusFilter) queryParams += `&reviewStatus=${reviewStatusFilter}`;

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
        }
    }, [currentPage, itemsPerPage, facultyFilter, startDate, endDate, sortOrder, reviewStatusFilter]);
  
    useEffect(() => {
        fetchSubmissions();
    }, [fetchSubmissions]);

    
   // Sort and filter the fetched submissions.
   
    useEffect(() => {
        let sortedSubmissions = sortSubmissionsByDate(submissions, sortOrder);
        let filteredSubmissions = filterSubmissionsByReviewStatus(sortedSubmissions, reviewStatusFilter);
        setDisplayedSubmissions(filteredSubmissions);
    }, [submissions, sortOrder, reviewStatusFilter]);

    
    
    //Select Submission to be displayed below table
    const selectSubmission = (submission) => {
        if (selectedSubmissions.length < 3 && !selectedSubmissions.find(sub => sub.submissionId === submission.submissionId)) {
            setSelectedSubmissions(prevSubmissions => [...prevSubmissions, submission]);
        }
    };
     //Remove that selected Submission
    const removeSelectedSubmission = (submissionIdToRemove) => {
        setSelectedSubmissions(selectedSubmissions.filter(sub => sub.submissionId !== submissionIdToRemove));

    };

    //For Search bar, not yet implemented
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
        // TODO: Implement search functionality
    };


    //Donwload Files of Selected Submission
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

      // Functions to handle track and reject 
      const trackSubmission = async (submissionId) => {
        const token = localStorage.getItem('userToken');
        try {
            // Append submissionId as a query parameter in the URL
            const url = `http://localhost:8080/api/tracking/track?submissionId=${submissionId}`;
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`
                    // Note: 'Content-Type': 'application/json' is not needed here 
                    // because we're not sending a JSON body anymore
                }
            });
    
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
    
            const trackedSubmission = await response.json();
            console.log('Submission tracked:', trackedSubmission);
            // Handle success - maybe update state or show a success message
        } catch (error) {
            console.error('Failed to track submission:', error);
            // Handle error - show error message to user
        }
    };
    const rejectSubmission = (submissionId) => {
       
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
        //fetchSubmissions(); 
    };

      // Adjust the button handlers 
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

     // Function to sort submissions by date
     const sortSubmissionsByDate = (submissions, sortOrder) => {
        return [...submissions].sort((a, b) => {
            const dateA = new Date(a.submissionDate);
            const dateB = new Date(b.submissionDate);
            return sortOrder === 'recent' ? dateB - dateA : dateA - dateB;
        });
    };

    // Function to filter submissions by review status
    const filterSubmissionsByReviewStatus = (submissions) => {
        return reviewStatusFilter
        ? submissions.filter(sub => sub.reviewStatus === reviewStatusFilter)
        : submissions;
    };

    const handleSortOrderChange = (newSortOrder) => {
        setSortOrder(newSortOrder);
        setCurrentPage(1); 
        fetchSubmissions(newSortOrder); 
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

                    {/* Sorting by date */}
                    <select value={sortOrder} onChange={e => handleSortOrderChange(e.target.value)}>
                    <option value="recent">Most Recent</option>
                    <option value="oldest">Oldest</option>
                    </select>
                    {/* Filtering by review status */}
                    <select value={reviewStatusFilter} onChange={e => setReviewStatusFilter(e.target.value)}>
                    <option value="">All Statuses</option>
                    <option value="Pending">Pending</option>
                    <option value="Accepted">Accepted</option>
                    <option value="Rejected">Rejected</option>
                    </select>

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
                
                </div>
            </div>

            <div className="dashboard-main">


                <div className="submissions-table-container">
                    
                    <table className="submissions-table">
                    <thead>
                        <tr>
                            <th>Applicant ID</th>
                            <th>Applicant Name</th>
                            <th>Supervisor Name</th>
                            <th>Faculty</th>
                            <th>Department</th>
                            <th>Submission Date</th>
                            <th>Review Status</th>
                            <th>Number of Files</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    {displayedSubmissions.map((submission) => (
                        <tr key={submission.submissionId}>
                        <td>{submission.studentId}</td>
                        <td>{submission.applicantName}</td>
                        <td>{submission.supervisorName}</td>
                        <td>{submission.faculty}</td>
                        <td>{submission.department}</td>
                        <td>{formatDate(submission.submissionDate)}</td>                        
                        <td>{submission.reviewStatus}</td> 
                        <td>{submission.fileMetadataList?.length || 0}</td> 
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

                <div className="selected-submissions-container">
                {selectedSubmissions.map((submission, index) => (
                    <div key={index} className="submission-details">
                        <p>Applicant ID: {submission.studentId}</p>
                        <p>Faculty: {submission.faculty}</p>
                        <p>Department: {submission.department}</p>
                        <p>Number of Files: {submission.fileMetadataList?.length || 0}</p>
                        <ul>
                            {submission.fileMetadataList?.map(file => (
                                <li key={file.id}>
                                    {file.fileName} - {file.fileType}
                                </li>
                            ))}
                        </ul>
                        <div className="submission-actions">
                            <button onClick={() => downloadFiles(submission.submissionId)}>
                                Download Files
                            </button>
                            <button onClick={() => trackSubmission(submission.submissionId)}>
                                Track Submission
                            </button>
                            <button onClick={() => rejectSubmission(submission.submissionId)}>
                                Reject Submission
                            </button>
                            <button onClick={() => removeSelectedSubmission(submission.submissionId)} className="remove-btn">
                                   &#x2715; 
                            </button>
                        </div>
                    </div>
                ))}
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
        </div>

    );
}

export default SubmissionDashboard;