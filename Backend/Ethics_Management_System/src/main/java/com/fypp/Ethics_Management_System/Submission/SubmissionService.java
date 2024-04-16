package com.fypp.Ethics_Management_System.Submission;

import com.fypp.Ethics_Management_System.Security.TokenProvider;
import com.fypp.Ethics_Management_System.UserResLogin.User;
import com.fypp.Ethics_Management_System.UserResLogin.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AWSFileStorageService awsFileStorageService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final Logger log = LoggerFactory.getLogger(SubmissionService.class);

    @Transactional
    public SubmissionDTO createSubmission(Set<MultipartFile> files, int userId, String faculty, String department, String studentId, String applicantName, String supervisorName) {
        Submission submission = new Submission();
        submission.setUserId(userId);
        submission.setSubmissionDate(LocalDateTime.now());
        submission.setFaculty(faculty);
        submission.setDepartment(department);
        submission.setStudentId(studentId);
        submission.setApplicantName(applicantName);
        submission.setSupervisorName(supervisorName);

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

        try {
            submissionRepository.save(submission);
            log.info("Submission saved with ID {}", submission.getId());
        } catch (Exception e) {
            log.error("Error saving submission", e);
            throw e; // Rethrow the exception to ensure the transaction is rolled back
        }

        return convertToSubmissionDTO(submission);
    }

    public Page<SubmissionDTO> findAllSubmissions(Optional<String> faculty, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Pageable pageable,  Optional<String> reviewStatus) {
        // Convert LocalDate to LocalDateTime
        LocalDateTime startDateTime = startDate.map(date -> date.atStartOfDay()).orElse(null);
        LocalDateTime endDateTime = endDate.map(date -> date.atTime(23, 59, 59)).orElse(null);

        // Log the converted date times
        logger.info("Converted startDate: {}, endDate: {}", startDateTime, endDateTime);

        Specification<Submission> spec = Specification.where(SubmissionSpecifications.withFaculty(faculty.orElse("")))
                .and(SubmissionSpecifications.withinDateRange(startDateTime, endDateTime))
                .and(SubmissionSpecifications.withReviewStatus(reviewStatus.orElse("")));
        // Log the specification result
        logger.debug("Specifications: withFaculty: {}, withinDateRange: {} to {}", faculty.orElse("None"), startDateTime, endDateTime);

        Page<Submission> submissions = submissionRepository.findAll(spec, pageable);

        // Log the actual content size
        logger.info("Fetched submissions size: {}", submissions.getSize());

        return submissions.map(this::convertToSubmissionDTO);
    }


    private SubmissionDTO convertToSubmissionDTO(Submission submission) {
        List<FileDTO> fileDTOs = submission.getFiles().stream()
                .map(this::convertToFileDTO)
                .collect(Collectors.toList());

        return new SubmissionDTO(submission.getId(), submission.getUserId(), fileDTOs, submission.getDepartment(), submission.getFaculty(), submission.getStudentId(), submission.getSubmissionDate(), submission.getReviewStatus(), submission.getApplicantName(), submission.getSupervisorName());
    }

    private FileDTO convertToFileDTO(File file) {
        return new FileDTO(file.getId(), file.getFileName(), file.getFileType(), file.getUserId(), file.getFilePath(), file.getUploadDate());
    }

    public List<SubmissionDTO> getSubmissionsByUserId(int userId) {
        return submissionRepository.findByUserId(userId).stream()
                .map(this::convertToSubmissionDTO)
                .collect(Collectors.toList());
    }


    public List<SubmissionDTO> getAllSubmissions() {
        return submissionRepository.findAll().stream()
                .map(this::convertToSubmissionDTO)
                .collect(Collectors.toList());
    }

    public void deleteSubmission(Long submissionId, String username) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not authenticated"));
        if (submission.getUserId() != user.getId()) {
            throw new RuntimeException("User does not have permission to delete this submission");
        }
        submissionRepository.delete(submission);
    }

    // Analytics Dashboard
    public Map<String, Long> getSubmissionVolumesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = submissionRepository.countBySubmissionDateBetweenGroupByFaculty(startDate, endDate);
        Map<String, Long> volumes = new LinkedHashMap<>(); // Maintain order of insertion
        for (Object[] result : results) {
            volumes.put((String) result[0], (Long) result[1]);
        }
        return volumes;
    }

    // Submission Dashboard Search
    public Page<SubmissionDTO> searchByApplicantNameOrStudentId(Optional<String> applicantName, Optional<String> studentId, Pageable pageable) {
        if (applicantName.isPresent()) {
            String searchPattern = "%" + applicantName.get() + "%";
            logger.info("Executing search with pattern: {}", searchPattern);
            try {
                Page<Submission> results = submissionRepository.findByApplicantNameContainingOrStudentIdContaining(searchPattern, searchPattern, pageable);
                logger.info("Database query executed successfully, converting results to DTOs");
                return results.map(this::convertToSubmissionDTO);
            } catch (Exception e) {
                logger.error("An error occurred during search query execution: {}", e.getMessage(), e);
                throw new RuntimeException("Database query failed", e);
            }
        } else {
            logger.warn("Search term not provided, returning empty results");
            return Page.empty(pageable);
        }
    }


}
