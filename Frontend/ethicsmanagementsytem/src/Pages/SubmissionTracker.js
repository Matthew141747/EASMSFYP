import React, { useState, useEffect, useCallback } from 'react';
import '../Styling/SubmissionTracker.css'; 
import FolderSection from '../Components/FolderSection';
import SubmissionCard from '../Components/SubmissionCard'; 
import { useNavigate } from 'react-router-dom';

const SubmissionTracker = () => {
  const [submissions, setSubmissions] = useState([]);
  const [currentFolder, setCurrentFolder] = useState(null);
  const [folders, setFolders] = useState([]); 
  const [filterNoFolder, setFilterNoFolder] = useState(false);  // State to manage filtering
  const token = localStorage.getItem('userToken');

  // Fetch all tracked submissions when the component mounts
  useEffect(() => {
    //fetchAllTrackedSubmissions();
    fetchFolders();
    if (currentFolder === 'all') {
      fetchAllTrackedSubmissions();
    } else if (currentFolder !== null) {
      fetchSubmissionsForFolder(currentFolder);
     // console.log('What is states here', currentFolder);
    }

 }, [currentFolder]);

 const navigate = useNavigate();

 useEffect(() => {
  // Redirect user to login page if not logged in
  if (!token) {
      navigate('/login');
  }
}, [token, navigate]); // The effect will run on component mount and whenever the token changes

  const fetchSubmissionsForFolder = useCallback(async (folderId) => {
    if (folderId === 'all') return;

    const token = localStorage.getItem('userToken');
    try {
      const response = await fetch(`http://localhost:8080/api/tracking/folder/${folderId}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      if (!response.ok) throw new Error('Failed to fetch submissions for folder');
      const data = await response.json();
      setSubmissions(data); 
      //console.log('Folder Submissions Look');
      //console.log(submissions);
    } catch (error) {
      console.error("Error fetching submissions for folder", error);
    }
  }, []);


   const fetchFolders = useCallback (async () => {
    const token = localStorage.getItem('userToken');
    try {
      const response = await fetch('http://localhost:8080/api/folders', {
        method: 'GET',
        headers: { 'Authorization': `Bearer ${token}` }
      });
      if (!response.ok) throw new Error('Failed to fetch folders');
      const data = await response.json();
      //console.log('Fetched folders in SubmissionTracker:', data); 
      setFolders(data);
    } catch (error) {
      console.error("Error fetching folders", error);
    }
   }, []);

  const fetchAllTrackedSubmissions = useCallback(async () => {
    const token = localStorage.getItem('userToken');
    try {
      const response = await fetch(`http://localhost:8080/api/tracking/allTrackedSubmissions`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`, 
        }
      });
      if (!response.ok) throw new Error('Failed to fetch submissions');
      const data = await response.json();
      setSubmissions(data);
      //console.log(submissions)
    } catch (error) {
      console.error("Error fetching submissions", error);
    }
  }, []);

  const handleMoveToFolder = async (submissionId, folderId) => {
    const token = localStorage.getItem('userToken');
    try {
      const url = new URL(`http://localhost:8080/api/tracking/folder/${folderId}/submission`);
      url.searchParams.append('submissionId', submissionId); 
  
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
        }
      });
  
      if (!response.ok) {
        const errorData = await response.text(); 
        throw new Error(errorData || 'Error moving submission to folder');
      }else{
        //const folderName = folders.find(folder => folder.id === folderId)?.name;
        // refetch the submissions to update the UI
        fetchAllTrackedSubmissions();
      }
    } catch (error) {
      console.error("Error moving submission to folder", error);
    }
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

const handleRemoveFromFolder = async (submissionId, folderId) => {
  console.log('Removing submission', submissionId, 'from folder', folderId);

  //const folderId = folders.find(folder => folder.name === folderName)?.id;
  if (!folderId) {
    console.error("Error: Folder not found");
    return;
  }

  const token = localStorage.getItem('userToken');

  try {
    
  const url = new URL(`http://localhost:8080/api/tracking/folder/${folderId}/submission/`);
  url.searchParams.append('submissionId', submissionId); 

  const response = await fetch(url, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`,
    }
  });

  if (!response.ok) {
    const errorData = await response.text(); 
    throw new Error(errorData || 'Error moving submission to folder');

  } else{
    fetchSubmissionsForFolder(folderId);
  }

  } catch (error) {
    console.error("Error removing submission from folder", error);
  }
};

const handleUntrack = async (submissionId) => {
  const token = localStorage.getItem('userToken');
  try {
    const response = await fetch(`http://localhost:8080/api/tracking/untrack?submissionId=${submissionId}`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
      }
    });

    if (!response.ok) {
      const errorData = await response.text();
      throw new Error(errorData || 'Error untracking submission');
    } else {
      // Update state to reflect that submission is no longer tracked
      setSubmissions(submissions => submissions.filter(sub => sub.submissionId !== submissionId));
    }
  } catch (error) {
    console.error("Error untracking submission", error);
  }
};

//const currentFolderId = currentFolder ? folders.find(folder => folder.name === currentFolder)?.id : null;
        
  //console.log('SubmissionTracker rendering');

  const handleUpdateStatus = async (submissionId, reviewStatus) => {
    const token = localStorage.getItem('userToken');
    try {
      const response = await fetch(`http://localhost:8080/api/tracking/review-status?submissionId=${submissionId}&reviewStatus=${reviewStatus}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
  
      if (!response.ok) {
        const errorData = await response.text();
        throw new Error(errorData || 'Error updating review status');
      } else {
        const updatedSubmission = await response.json();
        // Update the submissions state with the updated submission
        setSubmissions(submissions => submissions.map(sub => {
          if (sub.submissionId === submissionId) {
            return { ...sub, reviewStatus: updatedSubmission.reviewStatus };
          }
          return sub;
        }));
      }
    } catch (error) {
      console.error("Error updating review status", error);
    }
  };

  const displayedSubmissions = filterNoFolder
  ? submissions.filter(sub => sub.folderNames.length === 0)
  : submissions;


  return (
    <div className="submission-tracker">
    <FolderSection setCurrentFolder={setCurrentFolder} folders={folders} fetchFolders={fetchFolders} currentFolder={currentFolder} setSubmissions={setSubmissions} />

    <section className="submissions-section">

    <div className="section-header">
      <h3>
        {currentFolder ? (currentFolder === 'all' ? 'All Tracked Submissions' : `Submissions in "${folders.find(folder => folder.id === currentFolder)?.name}"`) : 'Select a folder to view submissions.'}
      </h3>

      <div>Total Submissions: {submissions.length}</div>

      <button onClick={() => setCurrentFolder('all')} className="show-all-btn">Show All Tracked Submissions</button>

          {currentFolder === 'all' && (
            <div className="filter-no-folder">
              <label>
                <input
                  type="checkbox"
                  checked={filterNoFolder}
                  onChange={() => setFilterNoFolder(!filterNoFolder)}
                />
                Not in Folder
              </label>
            </div>
          )}
      </div>

      <div className="submission-cards-container">
      {displayedSubmissions.length > 0 ? displayedSubmissions.map(submission => (
          <SubmissionCard key={submission.submissionId} submission={submission} folders={folders} 
          onMove={handleMoveToFolder} 
          downloadFiles={downloadFiles}  
          currentFolder={currentFolder === 'all' ? null : currentFolder}
          onUntrack={handleUntrack} 
          onRemoveFromFolder={handleRemoveFromFolder}
          onUpdateReviewStatus={handleUpdateStatus}
          />

        )) : <p>No submissions in this folder.</p>}
    
    </div>
    </section>
  </div>
  );
};

export default SubmissionTracker;