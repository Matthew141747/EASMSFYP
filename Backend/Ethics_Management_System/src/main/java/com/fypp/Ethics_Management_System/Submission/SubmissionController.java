package com.fypp.Ethics_Management_System.Submission;

import com.fypp.Ethics_Management_System.UserResLogin.User;
import com.fypp.Ethics_Management_System.UserResLogin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserService userService;

    @PostMapping("upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") Set<MultipartFile> files, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        SubmissionDTO submissionDTO = submissionService.createSubmission(files, user.getId());

        return ResponseEntity.ok(submissionDTO);
    }

}
