package com.fypp.Ethics_Management_System.Parse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/parsing")
public class ParseController {

    private final ParseService parseService;

    @Autowired
    public ParseController(ParseService parseService) {
        this.parseService = parseService;
    }

    @PostMapping("/parsePDF")
    public ResponseEntity<?> parsePDF(@RequestParam("file") MultipartFile file) {
        try {
            ExpeditedEthicsApplication application = parseService.parsePDF(file);

            ApplicationValidator.ValidationResults validationResults = ApplicationValidator.validateApplication(application);
            Map<String, Object> response = new HashMap<>();
            response.put("application", application);
            response.put("validationResults", validationResults);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during PDF parsing: " + e.getMessage());
        }
    }
}