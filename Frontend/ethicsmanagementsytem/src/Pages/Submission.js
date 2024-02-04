import '../Styling/SubmissionPage.css';
import React, { useState } from 'react';

function SubmissionPage() {
    const [selectedFiles, setSelectedFiles] = useState([]);
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
                <h2>Submit Your Documents</h2>
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