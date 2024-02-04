package com.fypp.Ethics_Management_System.Submission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//Can be removed

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public File storeFile(MultipartFile file, int userId) {
        // Create a FileEntity to be saved in the database
        File fileEntity = new File();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setUserId(userId);

        return fileRepository.save(fileEntity);
    }

    public File getFile(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
    }

}

