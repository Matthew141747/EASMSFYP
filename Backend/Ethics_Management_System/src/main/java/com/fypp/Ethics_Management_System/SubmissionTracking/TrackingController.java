package com.fypp.Ethics_Management_System.SubmissionTracking;
import com.fypp.Ethics_Management_System.Submission.Submission;
import com.fypp.Ethics_Management_System.Submission.SubmissionDTO;
import com.fypp.Ethics_Management_System.UserResLogin.User;
import com.fypp.Ethics_Management_System.UserResLogin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService trackingService;
    private final UserService userService;
    @Autowired
    public TrackingController(TrackingService trackingService, UserService userService) {
        this.trackingService = trackingService;
        this.userService = userService;
    }

    @GetMapping("/allTrackedSubmissions")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<List<SubmissionDTO>> getAllTrackedSubmissions() {
        User user = userService.getAuthenticatedUser(); // This line is using userService to get the authenticated user
        Integer facultyUserId = user.getId(); // Retrieves the ID from the authenticated user
        List<SubmissionDTO> trackedSubmissions = trackingService.getTrackedSubmissions(facultyUserId);
        return ResponseEntity.ok(trackedSubmissions);
    }

    @GetMapping("/folder/{folderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<List<SubmissionDTO>> getTrackedSubmissionsForFolder(
            @PathVariable Integer folderId) {
        User user = userService.getAuthenticatedUser();
        Integer facultyUserId = user.getId();
        List<SubmissionDTO> submissionsInFolder = trackingService.getTrackedSubmissionsForFolder(facultyUserId, folderId);
        return ResponseEntity.ok(submissionsInFolder);
    }

    @PostMapping("/track")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<TrackedSubmission> trackSubmission(@RequestParam Long submissionId) {
        User user = userService.getAuthenticatedUser();
        Integer facultyUserId = user.getId();
        TrackedSubmission tracked = trackingService.trackSubmission(submissionId, facultyUserId);
        return ResponseEntity.ok(tracked);
    }

    @PostMapping("/untrack")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<Void> untrackSubmission(
            @RequestParam Long submissionId) {
        User user = userService.getAuthenticatedUser();
        Integer facultyUserId = user.getId();
        trackingService.untrackSubmission(submissionId, facultyUserId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/review-status")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<SubmissionDTO> updateReviewStatus(@RequestParam Long submissionId, @RequestParam String reviewStatus) {
        Submission updatedSubmission = trackingService.updateReviewStatus(submissionId, reviewStatus);
        SubmissionDTO submissionDTO = trackingService.convertToSubmissionDTO(updatedSubmission);
        return ResponseEntity.ok(submissionDTO);
    }

    @PostMapping("/folder/{folderId}/submission")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<FolderSubmission> addSubmissionToFolder(@PathVariable Integer folderId, @RequestParam Long submissionId) {
        User user = userService.getAuthenticatedUser();
        FolderSubmission folderSubmission = trackingService.addSubmissionToFolder(folderId, submissionId, user.getId());
        return ResponseEntity.ok(folderSubmission);
    }

    @DeleteMapping("/folder/{folderId}/submission/")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<Void> removeSubmissionFromFolder(@PathVariable Integer folderId, @RequestParam  Long submissionId) {
        User user = userService.getAuthenticatedUser();
        trackingService.removeSubmissionFromFolder(folderId, submissionId, user.getId());
        return ResponseEntity.ok().build();
    }

}