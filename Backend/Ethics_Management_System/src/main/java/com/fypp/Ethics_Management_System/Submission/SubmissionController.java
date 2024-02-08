package com.fypp.Ethics_Management_System.Submission;

import com.fypp.Ethics_Management_System.UserResLogin.User;
import com.fypp.Ethics_Management_System.UserResLogin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserService userService;

    @PostMapping("upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") Set<MultipartFile> files,
                                             @RequestParam("faculty") String faculty,
                                             @RequestParam("department") String department,
                                             @RequestParam("studentId") String studentId,
                                             Authentication authentication) {

        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        SubmissionDTO submissionDTO = submissionService.createSubmission(files, user.getId(), faculty, department, studentId);

        return ResponseEntity.ok(submissionDTO);
    }

    @GetMapping("/All")
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions() {
        return ResponseEntity.ok(submissionService.getAllSubmissions());
    }

    @GetMapping("/by-faculty")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByFaculty(@RequestParam String faculty) {
        return ResponseEntity.ok(submissionService.getSubmissionsByFaculty(faculty));
    }

    @GetMapping("/by-department")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByDepartment(@RequestParam String department) {
        return ResponseEntity.ok(submissionService.getSubmissionsByDepartment(department));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate submissionDate) {
        return ResponseEntity.ok(submissionService.getSubmissionsByDate(submissionDate));
    }

}
