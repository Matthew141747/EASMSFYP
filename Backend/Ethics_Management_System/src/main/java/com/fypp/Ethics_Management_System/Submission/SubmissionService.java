package com.fypp.Ethics_Management_System.Submission;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
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

    @Autowired
    private AWSFileStorageService awsFileStorageService;


    @Transactional
    public SubmissionDTO createSubmission(Set<MultipartFile> files, int userId, String faculty, String department, String studentId) {
        Submission submission = new Submission();
        submission.setUserId(userId);
        submission.setSubmissionDate(LocalDateTime.now());
        submission.setFaculty(faculty);
        submission.setDepartment(department);
        submission.setStudentId(studentId);

        files.forEach(file -> {
            File fileEntity = new File();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFileType(file.getContentType());
            try {
                String filePath = awsFileStorageService.storeFile(file);
                fileEntity.setFilePath(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Error storing file: " + file.getOriginalFilename(), e);
            }
            fileEntity.setUploadDate(LocalDateTime.now());
            fileEntity.setUserId(userId);
            submission.addFile(fileEntity);
        });

        submissionRepository.save(submission);

        return convertToSubmissionDTO(submission);
    }


    private SubmissionDTO convertToSubmissionDTO(Submission submission) {
        List<FileDTO> fileDTOs = submission.getFiles().stream()
                .map(this::convertToFileDTO)
                .collect(Collectors.toList());

        return new SubmissionDTO(submission.getId(), submission.getUserId(), fileDTOs, submission.getDepartment(), submission.getFaculty(), submission.getStudentId(), submission.getSubmissionDate());
    }

    private FileDTO convertToFileDTO(File file) {
        return new FileDTO(file.getId(), file.getFileName(), file.getFileType(), file.getUserId(), file.getFilePath(), file.getUploadDate());
    }


    public List<SubmissionDTO> getAllSubmissions() {
        return submissionRepository.findAll().stream()
                .map(this::convertToSubmissionDTO)
                .collect(Collectors.toList());
    }

    public List<SubmissionDTO> getSubmissionsByFaculty(String faculty) {
        return submissionRepository.findByFaculty(faculty).stream()
                .map(this::convertToSubmissionDTO)
                .collect(Collectors.toList());
    }

    public List<SubmissionDTO> getSubmissionsByDepartment(String department) {
        return submissionRepository.findByDepartment(department).stream()
                .map(this::convertToSubmissionDTO)
                .collect(Collectors.toList());
    }

    public List<SubmissionDTO> getSubmissionsByDate(LocalDate submissionDate) {
        return submissionRepository.findBySubmissionDate(submissionDate).stream()
                .map(this::convertToSubmissionDTO)
                .collect(Collectors.toList());
    }


}
