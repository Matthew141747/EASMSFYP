package com.fypp.Ethics_Management_System.SubmissionTracking;

import com.amazonaws.services.glue.model.EntityNotFoundException;
import com.fypp.Ethics_Management_System.Submission.*;
import com.fypp.Ethics_Management_System.UserResLogin.User;
import com.fypp.Ethics_Management_System.UserResLogin.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class TrackingService {

    private final FolderRepository folderRepository;
    private final FolderSubmissionRepository folderSubmissionRepository;
    private final SubmissionRepository submissionRepository;
    private final TrackedSubmissionRepository trackedSubmissionRepository;

    @Autowired
    public TrackingService(FolderRepository folderRepository,
                           FolderSubmissionRepository folderSubmissionRepository,
                           SubmissionRepository submissionRepository,
                           TrackedSubmissionRepository trackedSubmissionRepository) { // Constructor injection
        this.folderRepository = folderRepository;
        this.folderSubmissionRepository = folderSubmissionRepository;
        this.submissionRepository = submissionRepository;
        this.trackedSubmissionRepository = trackedSubmissionRepository;
    }

    public List<SubmissionDTO> getTrackedSubmissions(Integer facultyUserId) {
        List<TrackedSubmission> trackedSubmissions = trackedSubmissionRepository.findByFacultyUserId(facultyUserId);
        return trackedSubmissions.stream()
                .map(trackedSubmission -> submissionRepository.findById(trackedSubmission.getSubmissionId()).orElse(null))
                .filter(Objects::nonNull)
                .map(this::convertToSubmissionDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TrackedSubmission trackSubmission(Long submissionId, Integer facultyUserId) {
        TrackedSubmission trackedSubmission = new TrackedSubmission();
        trackedSubmission.setSubmissionId(submissionId);
        trackedSubmission.setFacultyUserId(facultyUserId);
        trackedSubmission.setTrackedDate(LocalDateTime.now());
        return trackedSubmissionRepository.save(trackedSubmission);
    }

    @Transactional
    public void untrackSubmission(Long submissionId, Integer facultyUserId) {
        TrackedSubmission trackedSubmission = trackedSubmissionRepository
                .findBySubmissionIdAndFacultyUserId(submissionId, facultyUserId)
                .orElseThrow(() -> new EntityNotFoundException("Tracked submission not found"));
        trackedSubmissionRepository.delete(trackedSubmission);
    }

    @Transactional
    public Submission updateReviewStatus(Long submissionId, String reviewStatus) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new EntityNotFoundException("Submission not found with id: " + submissionId));
        submission.setReviewStatus(reviewStatus);
        return submissionRepository.save(submission);
    }

    public List<SubmissionDTO> getTrackedSubmissionsForFolder(Integer facultyUserId, Integer folderId) {
        // Check if the folder belongs to the faculty user before proceeding
        Folder folder = folderRepository.findByIdAndFacultyUserId(folderId, facultyUserId)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found or does not belong to the faculty user"));

        // Find all FolderSubmissions for the folder
        List<FolderSubmission> folderSubmissions = folderSubmissionRepository.findByFolderId(folder.getId());

        // Convert each FolderSubmission to SubmissionDTO
        return folderSubmissions.stream()
                .map(FolderSubmission::getSubmissionId)
                .map(submissionId -> submissionRepository.findById(submissionId)
                        .orElseThrow(() -> new EntityNotFoundException("Submission not found with id: " + submissionId))
                )
                .map(this::convertToSubmissionDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FolderSubmission addSubmissionToFolder(Integer folderId, Long submissionId, Integer facultyUserId) {
        Folder folder = folderRepository.findByIdAndFacultyUserId(folderId, facultyUserId)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found or does not belong to the faculty user"));

        FolderSubmission folderSubmission = new FolderSubmission(folderId, submissionId);
        return folderSubmissionRepository.save(folderSubmission);
    }

    @Transactional
    public void removeSubmissionFromFolder(Integer folderId, Long submissionId, Integer facultyUserId) {
        // Validate both the folder and submission belong to the faculty user
        Folder folder = folderRepository.findByIdAndFacultyUserId(folderId, facultyUserId)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found or does not belong to the faculty user"));

        // Find the FolderSubmission based on folderId and submissionId
        FolderSubmission folderSubmission = folderSubmissionRepository.findByFolderIdAndSubmissionId(folderId, submissionId)
                .orElseThrow(() -> new EntityNotFoundException("Folder submission not found"));
        folderSubmissionRepository.delete(folderSubmission);
    }

    public SubmissionDTO convertToSubmissionDTO(Submission submission) {
        List<FileDTO> fileDTOs = submission.getFiles().stream()
                .map(this::convertToFileDTO) // Convert File to FileDTO
                .collect(Collectors.toList());

        // Fetch related folder names for this submission
        List<String> folderNames = folderSubmissionRepository.findBySubmissionId(submission.getId())
                .stream()
                .map(folderSubmission -> folderRepository.findById(folderSubmission.getFolderId())
                        .map(Folder::getName).orElse("Unknown"))
                .collect(Collectors.toList());

        // Create and return the SubmissionDTO
        return new SubmissionDTO(
                submission.getId(),
                submission.getUserId(),
                fileDTOs,
                submission.getDepartment(),
                submission.getFaculty(),
                submission.getStudentId(),
                submission.getSubmissionDate(),
                submission.getReviewStatus(),
                submission.getApplicantName(),
                submission.getSupervisorName(),
                folderNames
        );
    }

    private FileDTO convertToFileDTO(File file) {
        return new FileDTO(
                file.getId(),
                file.getFileName(),
                file.getFileType(),
                file.getUserId(),
                file.getFilePath(),
                file.getUploadDate()
        );
    }


}