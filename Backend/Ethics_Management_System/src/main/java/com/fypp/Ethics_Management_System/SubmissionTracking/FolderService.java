package com.fypp.Ethics_Management_System.SubmissionTracking;

import com.amazonaws.services.glue.model.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FolderService {

    private final FolderRepository folderRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public List<Folder> getFoldersByFacultyUserId(Integer facultyUserId) {
        return folderRepository.findByFacultyUserId(facultyUserId);
    }

    @Transactional
    public Folder createFolder(Folder folder) {
        return folderRepository.save(folder);
    }

    @Transactional
    public void deleteFolder(Integer folderId) {
        folderRepository.deleteById(folderId);
    }

    public Folder getFolderById(Integer id) {
        return folderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found with id: " + id));
    }


    public Folder getFolderByIdAndFacultyUserId(Integer folderId, Integer facultyUserId) {
        // Use the new method from FolderRepository
        return folderRepository.findByIdAndFacultyUserId(folderId, facultyUserId)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found or does not belong to the faculty user"));
    }
}