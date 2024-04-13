package com.fypp.Ethics_Management_System.SubmissionTracking;

import com.fypp.Ethics_Management_System.UserResLogin.User;
import com.fypp.Ethics_Management_System.UserResLogin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/folders")
public class FolderController {

    private static final Logger logger = LoggerFactory.getLogger(FolderController.class);

    private final FolderService folderService;
    private final UserService userService;

    @Autowired
    public FolderController(FolderService folderService, UserService userService) {
        this.folderService = folderService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<List<Folder>> getFolders() {
        User user = userService.getAuthenticatedUser();
        logger.debug("Fetching folders for user ID: {}", user.getId());
        List<Folder> folders = folderService.getFoldersByFacultyUserId(user.getId());
        if (folders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(folders);
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder) {
        User user = userService.getAuthenticatedUser();
        logger.debug("Creating folder: {}", folder.getName());
        folder.setCreatedDate(new Date());
        folder.setFacultyUserId(user.getId());
        Folder createdFolder = folderService.createFolder(folder);
        return ResponseEntity.ok(createdFolder);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_ADMIN')")
    public ResponseEntity<?> deleteFolder(@PathVariable int id) {
        User user = userService.getAuthenticatedUser();
        logger.debug("Deleting folder ID: {}", id);
        Folder folder = folderService.getFolderById(id);
        if (!folder.getFacultyUserId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        folderService.deleteFolder(id);
        return ResponseEntity.ok().build();
    }
}