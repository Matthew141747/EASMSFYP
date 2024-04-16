package com.fypp.Ethics_Management_System.Analytics.Controller;

import com.fypp.Ethics_Management_System.Analytics.DTO.AutomatedReviewResultsDTO;
import com.fypp.Ethics_Management_System.Analytics.Entity.AutomatedReviewResults;
import com.fypp.Ethics_Management_System.Analytics.Service.AutomatedReviewResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics/AutomatedReviewResults")
public class AutomatedReviewResultsController {

    private final AutomatedReviewResultsService service;

    @Autowired
    public AutomatedReviewResultsController(AutomatedReviewResultsService service) {
        this.service = service;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitAutomatedReviewResults(@RequestBody AutomatedReviewResultsDTO dto) {
        try {
            AutomatedReviewResults savedResults = service.saveAutomatedReviewResults(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedResults);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during submission: " + e.getMessage());
        }
    }
}
