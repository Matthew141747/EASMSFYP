import '../Styling/SubmissionPage.css';
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function SubmissionPage() {
    const [selectedFiles, setSelectedFiles] = useState([]);
    const [faculty, setFaculty] = useState('');
    const [department, setDepartment] = useState('');
    const [studentId, setStudentId] = useState('');
    const [applicantName, setApplicantName] = useState('');
    const [supervisorName, setSupervisorName] = useState('');

    const token = localStorage.getItem('userToken');
    const [parsedData, setParsedData] = useState({});
    const [isLoading, setIsLoading] = useState(false);
    const [reviewResults, setReviewResults] = useState(null);

    //const [submissionDate, setSubmissionDate] = useState(null);

    const navigate = useNavigate();
    useEffect(() => {
        // Redirect user to login page if not logged in
        if (!token) {
            navigate('/login');
        }
    }, [token, navigate]); // The effect will run on component mount and whenever the token changes

    const handleReview = async () => {
        setIsLoading(true); // Start loading
        const mainApplicationFile = selectedFiles.find(file => file.isMainApplication);
    
        if (!mainApplicationFile) {
            alert('Please select a main application file to review.');
            return;
        }
    
        const formData = new FormData();
        formData.append('file', mainApplicationFile.fileData); // Append only the main application file
    
        try {
            const response = await fetch('http://localhost:8080/api/parsing/parsePDF', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                body: formData,
            });
            if (response.ok) {
                const jsonResponse  = await response.json();
                setParsedData(jsonResponse.application);
                setReviewResults(jsonResponse.validationResults);
                console.log('Full Response',jsonResponse);
               // console.log(reviewResults);
                //setApplicantName(parsedData.supervisorApplicantDetails.applicantName);
                //setSupervisorName(parsedData.supervisorApplicantDetails.supervisorName);
                
            } else {
                setParsedData(`Failed to review document. Status: ${response.status}`);
            }
        } catch (error) {
            console.error('Error during review:', error);
            setReviewResults('Error during review process.');
        }finally {
            setIsLoading(false); // End loading
        }
    };

    useEffect(() => {
        //console.log(parsedData);
        //console.log(reviewResults);
        if (parsedData?.supervisorApplicantDetails) {
          setApplicantName(parsedData.supervisorApplicantDetails.applicantName);
          setSupervisorName(parsedData.supervisorApplicantDetails.supervisorName);
          setStudentId(parsedData.supervisorApplicantDetails.applicantID);

          //console.log(applicantName);
          //console.log(supervisorName);
        }
      }, [parsedData, reviewResults, applicantName, supervisorName]);

    const handleFileChange = (event) => {
        const newFiles = Array.from(event.target.files).map(file => ({
            fileData: file,
            fileName: file.name,
            fileSize: file.size,
            isMainApplication: file.type === 'application/pdf', // Only PDFs can be main applications
        }));
    
        const combinedFiles = [...selectedFiles, ...newFiles];
    
        // Get the largest PDF file to mark it as the main application
        const pdfFiles = combinedFiles.filter(file => file.fileData.type === 'application/pdf');
        if (pdfFiles.length) {
            pdfFiles.forEach(file => file.isMainApplication = false); // Reset the main application flag for all PDFs
            const largestPdfFile = pdfFiles.reduce((prev, current) => (prev.fileSize > current.fileSize ? prev : current));
            largestPdfFile.isMainApplication = true; // Set the largest PDF as the main application
        }
    
        setSelectedFiles(combinedFiles);
    };


    // this function converts bytes to a more file size on the file list
    const formatBytes = (bytes, decimals = 2) => {
        if (bytes === 0) return '0 Bytes';
        const k = 1024;
        const dm = decimals < 0 ? 0 : decimals;
        const sizes = ['Bytes', 'KB', 'MB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    };


    const removeFile = (index) => {
        // Remove file based on index
        setSelectedFiles(selectedFiles.filter((_, fileIndex) => index !== fileIndex));
      };

      const markAsMainApplication = (index) => {
        // Only allow PDFs to be marked as the main application
        if (selectedFiles[index].fileData.type === 'application/pdf') {
            setSelectedFiles(selectedFiles.map((file, fileIndex) => ({
                ...file,
                isMainApplication: fileIndex === index,
            })));
        } else {
            alert('Only a PDF can be set as the main application.');
        }
    };

    const handleSubmit = async (event) => {
        setIsLoading(true);
        event.preventDefault();
        if (!selectedFiles.length) {
            alert('Please select files to upload.');
            setIsLoading(false); 
            return;
        }

        const formData = new FormData();
        formData.append('faculty', faculty);
        formData.append('department', department);
        formData.append('studentId', studentId);
        formData.append('applicantName', applicantName);
        formData.append('supervisorName', supervisorName);

        
        selectedFiles.forEach(fileDetail => {
            formData.append('files', fileDetail.fileData); 
        });

        try {
            const response = await fetch('http://localhost:8080/api/submissions/upload', {
                method: 'POST',
                headers: { 'Authorization': `Bearer ${token}` },
                body: formData,
            });
            if (response.ok) {
                const data = await response.json(); // Submission DTO returned to get the Submission ID
                const submissionId = data.submissionId;
                console.log(data);
                const submissionDate = data.submissionDate;

                //setSubmissionDate(new Date(data.submissionDate));
                //console.log(submissionDate); 
               // console.log('Submission ID Check', submissionId);

                if (parsedData.humanParticipantsDetails) {
                    await handleHumanParticipantsSubmit(parsedData.humanParticipantsDetails.participantCriteria, submissionId,submissionDate);
                }

                if (parsedData.researchProjectInfo) {
                    await handleOverviewOfTheResearchProjectSubmit(parsedData.researchProjectInfo.researchTypes, submissionId,submissionDate);
                }

                //Some parsing is needed to strucutre the data for this API endpoint
                const preparedData = prepareResearchProjectInformation(parsedData.researchProjectInfo);
                //console.log('Prepared Data Check', preparedData);
                
                await handleResearchProjectInformationSubmit(preparedData, submissionId, submissionDate);

                // remember to finsish the automated review results 
                setSelectedFiles([]);
                setParsedData([]);
                alert('Files uploaded successfully');

            } else {
                alert('Failed to upload files.');
            }
        } catch (error) {
            console.error('Error uploading files:', error);
            alert('Error uploading files.');
        }finally {
            setIsLoading(false); 
        }
    };

    // Clear the file input after files are selected
    const clearFileInput = (event) => {
        event.target.value = null;
    };

    //Methods to handle the storage of Parsed Information of a users application.

    const handleHumanParticipantsSubmit = async (participantsData, submissionId, submissionDate) => {
        try {
            const response = await fetch('http://localhost:8080/api/analytics/HumanParticipants/submit', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    participantCriteria: participantsData,
                    submissionId: submissionId,
                    submissionDate: submissionDate
                }),
            });
    
            if (response.ok) {
                console.log('Human Participants submitted successfully');
            } else {
                console.log('Failed to Submit Human Participants');
            }
        } catch (error) {
            console.error('Error during submission:', error);
        }
    };

    const handleOverviewOfTheResearchProjectSubmit = async (overviewData, submissionId, submissionDate) => {
        try {
            const response = await fetch('http://localhost:8080/api/analytics/OverviewOfTheResearchProject/submit', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    researchTypes: overviewData, 
                    submissionId: submissionId,
                    submissionDate: submissionDate
                }),
            });
    
            if (response.ok) {
                console.log('Overview submitted successfully');
            } else {
                console.error('Failed to submit overview');
            }
        } catch (error) {
            console.error('Error during submission:', error);
        }
    };

    const handleResearchProjectInformationSubmit = async (ResearchData, submissionId, submissionDate) => {
        try {
            const response = await fetch('http://localhost:8080/api/analytics/ResearchProjectInformation/submit', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    ...ResearchData, 
                    submissionId: submissionId,
                    submissionDate: submissionDate
                }),
            });
    
            if (response.ok) {
                console.log('Research Project Information submitted successfully');
            } else {
                console.error('Failed to submit overview');
            }
        } catch (error) {
            console.error('Error during submission:', error);
        }
    };


    //Supporting Methods to prepare the Data for Research Project Information

        // Helper function to determine recording type for numerical format
        const determineRecordingType = (video, audio) => {
            if (video && audio) return 3;
            if (video) return 2;
            if (audio) return 1;
            return 0;
        };

        const prepareResearchProjectInformation = (info) => {
            return {
                participantsRecorded: info.recordingDetails.willParticipantsBeRecorded,
                recordingType: determineRecordingType(info.recordingDetails.videoRecording, info.recordingDetails.audioRecording),
                prototypeDeveloped: info.prototypeDetails.willPrototypeBeDeveloped,
                prototypeServiceFramework: info.prototypeDetails.prototypeTypes['Service/Framework'],
                prototypeDigitalUiApp: info.prototypeDetails.prototypeTypes['Digital UI/App'],
                prototypePhysicalArtifact: info.prototypeDetails.prototypeTypes['Physical artifact'],
                minimumParticipants: info.minimumParticipants,
                maximumParticipants: info.maximumParticipants,
                accommodationForNonParticipants: 0  
            };
        };

        /*
    const handleAutomatedReviewSubmit = async (ReviewData, submissionId) => {
        try {
            const response = await fetch('http://localhost:8080/api/analytics/AutomatedReviewResults/submit', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    participantCriteria: ReviewData,
                    submissionId: submissionId
                }),
            });
    
            if (response.ok) {
                console.log('Automated Review Results submitted successfully');
            } else {
                console.log('Failed to Submit Automated Review Results');
            }
        } catch (error) {
            console.error('Error during submission:', error);
        }
    };*/





    return (
        <div className="submission-page">
            <div className="submission-container">
                <h1>Submission</h1>
                <p>Guidelines:</p>
                <ol>
                    <li>Ensure your Expedited Ethics Application is in PDF format.</li>
                    <li>Include all required signatures and appendices.</li>
                    <li>Check that all sections are completed accurately.</li>
                    <li>Upload any necessary supporting documents.</li>
                </ol>
                <div className="submission-form-container">

                    <form onSubmit={handleSubmit} className="submission-form">
                        <h2>Make a submission</h2>
                        <div className="form-horizontal">
                            <div className="form-group">
                                <label htmlFor="facultySelect">Faculty:</label>
                                <select id="facultySelect" value={faculty} onChange={(e) => setFaculty(e.target.value)}>
                                <option value="">Select Faculty</option>
                                <option value="arts_humanities_social_sciences">Faculty of Arts, Humanities and Social Sciences</option>
                                <option value="education_health_sciences">Faculty of Education and Health Sciences</option>
                                <option value="kemmy_business_school">Kemmy Business School</option>
                                <option value="science_engineering">Faculty of Science and Engineering</option>
                                <option value="irish_world_academy">Irish World Academy of Music & Dance</option>
                                </select>
                            </div>
                            {faculty === 'science_engineering' && (
                                <div className="form-group">
                                    <label htmlFor="departmentSelect">Department:</label>
                                    <select id="departmentSelect" value={department} onChange={(e) => setDepartment(e.target.value)}>
                                    <option value="">Select Department</option>
                                    <option value="computer_science">Computer Science & Information Systems</option>
                                    <option value="electronic_engineering">Electronic & Computer Engineering</option>
                                    <option value="mathematics_statistics">Mathematics & Statistics</option>
                                    <option value="school_of_engineering">School of Engineering</option>
                                    <option value="school_of_design">School of Design</option>
                                    <option value="school_of_natural_sciences">School of Natural Sciences</option>
                                    </select>
                                </div>
                            )}
                        </div>

                        <div className="review-details">
                            <h3>Submission Details</h3>
                            <div className="details-grid">
                            <div className="details-column">
                                <p><strong>Study Title:</strong> {parsedData?.supervisorApplicantDetails?.studyTitle}</p>
                                <p><strong>Supervisor Name:</strong> {parsedData?.supervisorApplicantDetails?.supervisorName}</p>
                                <p><strong>Supervisor Email:</strong> {parsedData?.supervisorApplicantDetails?.supervisorEmail}</p>
                                <p><strong>Applicant Name:</strong> {parsedData?.supervisorApplicantDetails?.applicantName}</p>
                            </div>
                            <div className="details-column">
                                <p><strong>Applicant Name:</strong> {parsedData?.supervisorApplicantDetails?.applicantName}</p>
                                <p><strong>Applicant ID:</strong> {parsedData?.supervisorApplicantDetails?.applicantID}</p>
                                <p><strong>Applicant Email:</strong> {parsedData?.supervisorApplicantDetails?.applicantEmail}</p>
                            </div>
                            </div>
                        </div>

                            <div className="form-group">
                                <label htmlFor="fileInput">Upload PDFs:</label>
                                <input 
                                    type="file" 
                                    id="fileInput" 
                                    accept=".pdf, .png, .jpg" 
                                    multiple 
                                    onChange={handleFileChange} 
                                    onClick={clearFileInput} 
                                />
                            </div>

                            <div className="file-list">
                            {selectedFiles.map((fileDetail, index) => (
                                <div key={index} className="file-item">
                                <div className="file-details">
                                    <div className="file-name">{fileDetail.fileName}</div>
                                    <div className="file-size">{formatBytes(fileDetail.fileSize)}</div>
                                    <div className={`file-type ${fileDetail.isMainApplication ? 'main-application' : 'supporting-document'}`}>
                                    {fileDetail.isMainApplication ? 'Main Application' : 'Supporting Document'}
                                    </div>
                                </div>
                                <div className="file-actions">
                                    <button type="button" onClick={() => markAsMainApplication(index)}>Main</button>
                                    <button type="button" onClick={() => removeFile(index)}>Remove</button>
                                </div>
                                </div>
                            ))}
                            </div>

                            {isLoading && (
                                <div className="loading-indicator">
                                    Loading...
                                </div>
                            )}

                            <div className="form-action-buttons">
                            <button type="submit" className="submit-btn">Submit</button>
                            <button type="button" className="review-btn" onClick={handleReview}>Review</button>
                        </div>
                    </form>
                </div>

                <div className="review-results-form">
                    <h2>Automated Review Results</h2>
                    {reviewResults ? (
                        <div className="compliance-tests">
                            <h3>Compliance Tests</h3>
                            <ul>
                                <li>
                                <div className="test-status">
                                    Signatures Present:{" "}
                                    <span className={`status ${reviewResults.signaturesPresent ? "success" : "failure"}`}>
                                        {reviewResults.signaturesPresent ? "Yes" : "No"}
                                    </span>
                                    </div>
                                    <p className="test-description">
                                        This test verifies that all required signatures are present in your Expedited Ethics Application. 
                                    </p>
                                </li>

                                <li>
                                <div className="test-status">
                                    Correct Application Form Used:{" "}
                                    <span className={`status ${reviewResults.correctDocument ? "success" : "failure"}`}>
                                        {reviewResults.correctDocument ? "Yes" : "No"}
                                    </span>
                                    </div>
                                    <p className="test-description">
                                        This test checks whether the correct Application form has been used for the submission. If the result here is No, then you may need to use the Full Ethics Application Form.
                                    </p>
                                </li>

                                <li>
                                <div className="test-status">
                                    All Information Sheets Present:{" "}
                                    <span className={`status ${reviewResults.allInformationSheetsPresent ? "success" : "failure"}`}>
                                        {reviewResults.allInformationSheetsPresent ? "Yes" : "No"}
                                    </span>
                                    </div>
                                    <p className="test-description">
                                        This test ensures that all required informational sheets are included with the application. 
                                    </p>
                                </li>

                                <li>
                                <div className="test-status">
                                    All Consent Sheets Present:{" "}
                                    <span className={`status ${reviewResults.allConsentSheetsPresent ? "success" : "failure"}`}>
                                        {reviewResults.allConsentSheetsPresent ? "Yes" : "No"}
                                    </span>
                                    </div>
                                    <p className="test-description">
                                        This test confirms that all necessary consent sheets are present.
                                    </p>
                                </li>
                            </ul>
                        </div>
                    ) : (
                        <p>No review results available</p>
                    )}
                </div>
            </div>
        </div>
         
    );
}

export default SubmissionPage;