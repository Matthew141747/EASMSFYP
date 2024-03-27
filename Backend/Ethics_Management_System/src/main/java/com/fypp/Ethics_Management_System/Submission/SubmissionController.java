package com.fypp.Ethics_Management_System.Submission;

import com.fypp.Ethics_Management_System.Security.TokenProvider;
import com.fypp.Ethics_Management_System.UserResLogin.User;
import com.fypp.Ethics_Management_System.UserResLogin.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
                                             Authentication authentication) {

        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        SubmissionDTO submissionDTO = submissionService.createSubmission(files, user.getId(),
                                                             faculty, department, studentId);

        return ResponseEntity.ok(submissionDTO);
    }

    @GetMapping("/All")
    public ResponseEntity<Page<SubmissionDTO>> getAllSubmissions(
            @RequestParam Optional<String> faculty,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        //Pageable pageable
        //@PageableDefault(size = 10)
        //pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());

        Pageable pageable = PageRequest.of(page, size);
        logger.info("Received pageable: page {}, size {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<SubmissionDTO> submissionPage  = submissionService.findAllSubmissions(faculty, startDate, endDate, pageable);
        return ResponseEntity.ok(submissionPage );
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
