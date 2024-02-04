package com.fypp.Ethics_Management_System.Submission;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private FileRepository fileRepository;


    @Transactional
    public SubmissionDTO createSubmission(Set<MultipartFile> files, int userId) {
        Submission submission = new Submission();
        submission.setUserId(userId);
        submission.setSubmissionDate(LocalDateTime.now());

        files.forEach(file -> {
            File fileEntity = new File();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFileType(file.getContentType());
            String filePath = storeFile(file);
            fileEntity.setFilePath(filePath);
            fileEntity.setUploadDate(LocalDateTime.now());
            fileEntity.setUserId(userId);
            submission.addFile(fileEntity);
        });

        submissionRepository.save(submission);

        return convertToSubmissionDTO(submission);
    }

    private String storeFile(MultipartFile file) {
        // Placeholder for file storage logic
        // Return the file path or URL where the file is stored
        return "path/to/file";
    }

    private SubmissionDTO convertToSubmissionDTO(Submission submission) {
        List<FileDTO> fileDTOs = submission.getFiles().stream()
                .map(this::convertToFileDTO)
                .collect(Collectors.toList());

        return new SubmissionDTO(submission.getId(), submission.getUserId(), fileDTOs);
    }

    private FileDTO convertToFileDTO(File file) {
        return new FileDTO(file.getId(), file.getFileName(), file.getFileType(), file.getUserId(), file.getFilePath(), file.getUploadDate());
    }


}
