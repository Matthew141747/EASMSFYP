import React from 'react';
import { render, screen, fireEvent, waitFor  } from '@testing-library/react';
import SubmissionCard from '../Components/SubmissionCard';
describe('SubmissionCard', () => {
    const mockSubmission = {
      submissionId: '22',
      title: 'A Study of AI',
      studentId: '20267126',
      applicantName: 'Matthew Healy-White',
      supervisorName: 'Dr. John Smith',
      submissionDate: new Date().toISOString(),
      faculty: 'Science_Engineering',
      department: 'Computer_Science',
      fileMetadataList: [
        { id: 'file1', fileName: 'Application.pdf', fileType: 'PDF' }
      ],
      reviewStatus: 'Pending',
      folderNames: []
    };
  
    const mockFolders = [
      { id: 'folder1', name: 'Folder 1' },
      { id: 'folder2', name: 'Folder 2' }
    ];
  
    const mockFunctions = {
      onMove: jest.fn(),
      onUntrack: jest.fn(),
      downloadFiles: jest.fn(),
      onRemoveFromFolder: jest.fn(),
      onUpdateReviewStatus: jest.fn()
    };
  
    beforeEach(() => {
      render(
        <SubmissionCard
          submission={mockSubmission}
          folders={mockFolders}
          onMove={mockFunctions.onMove}
          onUntrack={mockFunctions.onUntrack}
          downloadFiles={mockFunctions.downloadFiles}
          currentFolder="folder1"
          onRemoveFromFolder={mockFunctions.onRemoveFromFolder}
          onUpdateReviewStatus={mockFunctions.onUpdateReviewStatus}
        />
      );
    });
  
    it('renders submission details correctly', () => {
      expect(screen.getByText('A Study of AI')).toBeInTheDocument();
      expect(screen.getByText(/Matthew Healy-White/i)).toBeInTheDocument();
      expect(screen.getByText(/Science_Engineering/i)).toBeInTheDocument();
      expect(screen.getByText('Application.pdf (PDF)')).toBeInTheDocument();
    });
  
    it('handles status updates correctly', () => {
      fireEvent.change(screen.getByRole('combobox', { name: /update status/i }), { target: { value: 'Accept' } });
      expect(mockFunctions.onUpdateReviewStatus).toHaveBeenCalledWith('22', 'Accept');
    });
  
    it('initiates file download', () => {
      fireEvent.click(screen.getByText('Download Files'));
      expect(mockFunctions.downloadFiles).toHaveBeenCalledWith('22');
    });
  
    it('allows moving a submission to another folder', () => {
      fireEvent.change(screen.getByRole('combobox', { name: /move to folder/i }), { target: { value: 'folder2' } });
      expect(mockFunctions.onMove).toHaveBeenCalledWith('22', 'folder2');
    });
  
  });
