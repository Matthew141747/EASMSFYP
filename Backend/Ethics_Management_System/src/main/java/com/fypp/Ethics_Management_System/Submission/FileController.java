package com.fypp.Ethics_Management_System.Submission;

import com.fypp.Ethics_Management_System.UserResLogin.User;
import com.fypp.Ethics_Management_System.UserResLogin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/*
@RestController
@RequestMapping("/api/submissions")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService; // Assuming this service provides user details

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) {
        // Extract user details from the Authentication object
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        File fileEntity = fileService.storeFile(file, user.getId());

        // Return a DTO instead of the entity directly to avoid exposing the file data
        FileDTO fileDTO = new FileDTO(fileEntity.getId(), fileEntity.getFileName(), fileEntity.getFileType(), fileEntity.getUserId(), fileEntity.getFilePath(), fileEntity.getUploadDate());

        return new ResponseEntity<>(fileDTO, HttpStatus.CREATED);
    }

}
*/

