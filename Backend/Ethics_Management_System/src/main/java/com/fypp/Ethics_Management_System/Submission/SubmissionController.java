package com.fypp.Ethics_Management_System.Submission;

import com.fypp.Ethics_Management_System.Security.TokenProvider;
import com.fypp.Ethics_Management_System.UserResLogin.User;
import com.fypp.Ethics_Management_System.UserResLogin.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Optional;
@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);


    @PostMapping("upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") Set<MultipartFile> files,
                                             @RequestParam("faculty") String faculty,
                                             @RequestParam("department") String department,
                                             @RequestParam("studentId") String studentId,
                                             @RequestParam("applicantName") String applicantName,
                                             @RequestParam("supervisorName") String supervisorName,
                                             Authentication authentication) {

        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        SubmissionDTO submissionDTO = submissionService.createSubmission(files, user.getId(),
                                                             faculty, department, studentId, applicantName,supervisorName);

        return ResponseEntity.ok(submissionDTO);
    }

    @GetMapping("/All")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')") // Only allow users with ROLE_FACULTY or ROLE_ADMIN
    public ResponseEntity<Page<SubmissionDTO>> getAllSubmissions(
            @RequestParam Optional<String> faculty,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<String> reviewStatus
    ) {
        // Determine the direction of the sorting based on the 'sort' parameter
        Sort.Direction sortDirection = sort.isPresent() && "oldest".equals(sort.get())
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Create a Sort object with "submissionDate" and "reviewStatus"
        Sort sortObj = Sort.by(sortDirection, "submissionDate");
        if (reviewStatus.isPresent()) {
            sortObj = sortObj.and(Sort.by(sortDirection, "reviewStatus"));

        }
        // Create Pageable instance with sorting direction
        Pageable pageable = PageRequest.of(page, size, sortObj);

        // Log the received parameters
        logger.info("Received pageable: page {}, size {}, sort {}", pageable.getPageNumber(), pageable.getPageSize(), sort.orElse("recent"));

        // Call the service method with the updated pageable (including sorting)
        Page<SubmissionDTO> submissionPage = submissionService.findAllSubmissions(faculty, startDate, endDate, pageable, reviewStatus);

        // Return the paginated and sorted submissions
        return ResponseEntity.ok(submissionPage);
    }

    @GetMapping("/userSubmissions")
    public ResponseEntity<List<SubmissionDTO>> getUserSubmissions(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        List<SubmissionDTO> submissions = submissionService.getSubmissionsByUserId(user.getId());
        return ResponseEntity.ok(submissions);
    }

    @DeleteMapping("/{submissionId}")
    public ResponseEntity<?> deleteSubmission(@PathVariable Long submissionId, Authentication authentication) {
        submissionService.deleteSubmission(submissionId, authentication.getName());
        return ResponseEntity.ok().build();
    }


}
