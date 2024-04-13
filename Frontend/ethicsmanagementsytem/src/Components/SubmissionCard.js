import React, { useEffect, useMemo } from 'react';
import '../Styling/SubmissionCard.css'; 

const SubmissionCard = ({ submission, folders, onMove, onUntrack, downloadFiles, currentFolder, onRemoveFromFolder, onUpdateReviewStatus }) => {
    console.log('Current Submissions:',submission);
 
    console.log(currentFolder);

useEffect(() => {
    //console.log('Updated folders:', folders);
  }, [folders]);


  const currentFolderName = useMemo(() => {
    const folder = folders.find(folder => folder.id === currentFolder);
    return folder ? folder.name : '';
  }, [currentFolder, folders]);


  // This button will call the onRemoveFromFolder function passed down from SubmissionTracker

 // console.log('Current Folder:', currentFolder);
 // console.log('Current Folder ID:', currentFolderId);

  // This button will call the onRemoveFromFolder function passed down from SubmissionTracker
  /*
  const removeButton = currentFolder && (
    <button onClick={() => onRemoveFromFolder(submission.submissionId, currentFolder)}>
    Remove from "{currentFolderName}"
    </button>
  );*/

 const handleUpdateStatus = (e) => {
  const newStatus = e.target.value;
  onUpdateReviewStatus(submission.submissionId, newStatus); 
};

  const updateStatusOptions = ['Accept', 'Accept with Minor', 'Reject', 'Reject with Major', 'Reject with Minor'];


  return (
    <div className="submission-card">

      <div className="submission-header">
         <h5>{submission.title || 'Submission Title'}</h5>
          <div className="submission-info">
          <select className="move-to-folder-select" onChange={(e) => onMove(submission.submissionId, e.target.value)}>
          <option value="">Move to folder...</option>
          {folders.map(folder => (
            <option key={folder.id} value={folder.id}>{folder.name}</option>
          ))}
        </select>
        <p>Folders: {submission.folderNames.join(', ') || 'No folder'}</p>

        {currentFolder && (
          <button
            className="remove-button"
            onClick={() => onRemoveFromFolder(submission.submissionId, currentFolder)}
          >
            Remove from "{currentFolderName}"
          </button>
        )}
        </div>
      </div>

      <div className="submission-body">
        <div className="submission-details">
          <p>Student ID: {submission.studentId}</p>
          <p>Applicant: {submission.applicantName}</p>
          <p>Supervisor: {submission.supervisorName}</p>
          <p>Date: {new Date(submission.submissionDate).toLocaleDateString()}</p>
          <p>Faculty: {submission.faculty}</p>
          <p>Department: {submission.department}</p>
          <div className="file-list">
            Files:
            <ul>
              {submission.fileMetadataList.map(file => (
                <li key={file.id}>{file.fileName} ({file.fileType})</li>
              ))}
            </ul>
          </div>
        </div>

        <div className="submission-status">
        <p>Current Status of Submission: {submission.reviewStatus}</p>

          <select className="update-status-select" onChange={handleUpdateStatus }>
            <option value="">Update Status</option>
            {updateStatusOptions.map(status => (
              <option key={status} value={status}>{status}</option>
            ))}
          </select>

          <div className="submission-actions">
            <button onClick={() => onUntrack(submission.submissionId)}>Untrack Submission</button>
            <button onClick={() => downloadFiles(submission.submissionId)}>Download Files</button>
          </div>
        </div>

      </div>
    </div>
  );
};

export default React.memo(SubmissionCard);
