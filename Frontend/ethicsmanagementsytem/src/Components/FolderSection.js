// FolderSection.js
import React, { useState } from 'react';
import '../Styling/FolderSection.css'; 
const FolderSection = ({setCurrentFolder, folders, fetchFolders, currentFolder, setSubmissions }) => {
    
    //const [folders, setFolders] = useState([]);
    const [newFolderName, setNewFolderName] = useState('');

  const handleNewFolderChange = (e) => {
    setNewFolderName(e.target.value);
  };

  const handleFolderClick = (folderId) => {
    // Toggle the current folder: unselect if it's already selected otherwise select it
    if (currentFolder === folderId) {
      setCurrentFolder(null);
      setSubmissions([]); 
    } else {
      setCurrentFolder(folderId);
    }
  };

    const handleCreateFolder = async () => {

    const token = localStorage.getItem('userToken');
    try {

      const response = await fetch('http://localhost:8080/api/folders/create', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: newFolderName })
      });

      if (!response.ok) throw new Error('Failed to create folder');

      await response.json();
      setNewFolderName('');
      fetchFolders(); 

    } catch (error) {
      console.error("Error creating folder", error);
    }
  };

  const handleDeleteFolder = async (folderId) => {
    const token = localStorage.getItem('userToken');
    try {
      const response = await fetch(`http://localhost:8080/api/folders/${folderId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`
        },
      });
      if (!response.ok) throw new Error('Failed to delete folder');
      fetchFolders(); 
    } catch (error) {
      console.error("Error deleting folder", error);
    }
  };


  return (
    <aside className="folder-management">
    <h3>Folders</h3>
    <div className="create-folder">
      <input
        type="text"
        placeholder="New Folder Name"
        value={newFolderName}
        onChange={handleNewFolderChange}
        className="new-folder-input"
      />
      <button onClick={handleCreateFolder} className="create-folder-btn">Create</button>
    </div>

    <div className="folder-display">
                {folders.map(folder => (
                    <div 
                        key={folder.id} 
                        className={`folder-card ${currentFolder === folder.id ? 'selected-folder' : ''}`}
                        onClick={() => handleFolderClick(folder.id)}
                    >
                        <div className="folder-icon">📁</div>
                        <div className="folder-details">
                            <h5>{folder.name}</h5>
                            <p>{folder.submissionsCount || 0} submissions</p>
                            <button onClick={(e) => {e.stopPropagation(); handleDeleteFolder(folder.id);}} className="folder-delete-btn">Delete</button>
                        </div>
                        {currentFolder === folder.id && <span className="selected-indicator">✓</span>}
                    </div>
                ))}
            </div>
  </aside>
  );
};

export default React.memo(FolderSection);
