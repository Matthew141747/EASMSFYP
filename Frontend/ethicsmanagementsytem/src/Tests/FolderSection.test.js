import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import FolderSection from '../Components/FolderSection';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils';

// Mocks
const mockSetCurrentFolder = jest.fn();
const mockFetchFolders = jest.fn();
const mockSetSubmissions = jest.fn();

// Data
const folders = [
  { id: '1', name: 'Folder 1', submissionsCount: 3 },
  { id: '2', name: 'Folder 2', submissionsCount: 5 }
];

describe('FolderSection', () => {
  beforeEach(() => {
    render(<FolderSection setCurrentFolder={mockSetCurrentFolder} folders={folders} fetchFolders={mockFetchFolders} currentFolder={null} setSubmissions={mockSetSubmissions} />);
  });

  it('should render without crashing', () => {
    expect(screen.getByText('Folders')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('New Folder Name')).toBeInTheDocument();
  });

  it('should display list of folders', () => {
    expect(screen.getByText('Folder 1')).toBeInTheDocument();
    expect(screen.getByText('Folder 2')).toBeInTheDocument();
  });

  it('allows creating a new folder', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(),
      })
    );

    await act(async () => {
      fireEvent.change(screen.getByPlaceholderText('New Folder Name'), { target: { value: 'New Folder' } });
      fireEvent.click(screen.getByText('Create'));
    });

    await waitFor(() => {
      expect(global.fetch).toHaveBeenCalledWith(
        expect.any(String), 
        expect.objectContaining({
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('userToken')}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ name: 'New Folder' })
        })
      );
      expect(mockFetchFolders).toHaveBeenCalledTimes(1);
      expect(screen.getByPlaceholderText('New Folder Name').value).toBe(''); 
    });
  });

  it('handles folder selection', () => {
    fireEvent.click(screen.getByText('Folder 1'));
    expect(mockSetCurrentFolder).toHaveBeenCalledWith('1');
  });
});
