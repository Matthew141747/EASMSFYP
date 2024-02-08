import '../Styling/SubmissionPage.css';
import React, { useState } from 'react';

function SubmissionPage() {
    const [selectedFiles, setSelectedFiles] = useState([]);
    const [faculty, setFaculty] = useState('');
    const [department, setDepartment] = useState('');
    const [studentId, setStudentId] = useState('');
    const token = localStorage.getItem('userToken');

    const handleFileChange = (event) => {
        // Add new files to the existing array without replacing them
        setSelectedFiles(prevFiles => [...prevFiles, ...Array.from(event.target.files)]);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (!selectedFiles.length) {
            alert('Please select files to upload.');
            return;
        }
        const formData = new FormData();
        formData.append('faculty', faculty);
        formData.append('department', department);
        formData.append('studentId', studentId);
        selectedFiles.forEach(file => {
            formData.append('files', file);
        });

        try {
            const response = await fetch('http://localhost:8080/api/submissions/upload', {
                method: 'POST',
                headers: { 'Authorization': `Bearer ${token}` },
                body: formData,
            });
            if (response.ok) {
                alert('Files uploaded successfully');
                setSelectedFiles([]); // Clear the selected files after successful upload
            } else {
                alert('Failed to upload files.');
            }
        } catch (error) {
            console.error('Error uploading files:', error);
            alert('Error uploading files.');
        }
    };

    // Clear the file input after files are selected
    const clearFileInput = (event) => {
        event.target.value = null;
    };

    return (
        <div className="submission-page">
        <form onSubmit={handleSubmit} className="submission-form">
            <h2>Upload Your Documents</h2>
            
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

            <div className="form-group">
                <label htmlFor="studentIdInput">Student ID:</label>
                <input 
                    type="text" 
                    id="studentIdInput" 
                    value={studentId} 
                    onChange={(e) => setStudentId(e.target.value)} 
                    placeholder="Enter Student ID"
                />
            </div>
            
            {/* Existing file input and list code */}
            <div className="form-group">
                <label htmlFor="fileInput">Upload PDFs:</label>
                <input 
                    type="file" 
                    id="fileInput" 
                    accept=".pdf" 
                    multiple 
                    onChange={handleFileChange} 
                    onClick={clearFileInput} 
                />
            </div>
            <div className="file-list">
                {selectedFiles.map((file, index) => (
                    <div key={index} className="file-item">{file.name}</div>
                ))}
            </div>
            <button type="submit" className="submit-btn">Upload</button>
        </form>
    </div>
    );
}

export default SubmissionPage;