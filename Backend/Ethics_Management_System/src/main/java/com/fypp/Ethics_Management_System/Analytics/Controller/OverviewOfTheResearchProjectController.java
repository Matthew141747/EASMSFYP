package com.fypp.Ethics_Management_System.Analytics.Controller;

import com.fypp.Ethics_Management_System.Analytics.DTO.OverviewOfTheResearchProjectDTO;
import com.fypp.Ethics_Management_System.Analytics.Entity.OverviewOfTheResearchProject;
import com.fypp.Ethics_Management_System.Analytics.Service.OverviewOfTheResearchProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics/OverviewOfTheResearchProject")
public class OverviewOfTheResearchProjectController {

    private final OverviewOfTheResearchProjectService service;

    @Autowired
    public OverviewOfTheResearchProjectController(OverviewOfTheResearchProjectService service) {
        this.service = service;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitOverviewOfTheResearchProject(@RequestBody OverviewOfTheResearchProjectDTO dto) {
        try {
            OverviewOfTheResearchProject savedOverview = service.saveOverviewOfTheResearchProject(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOverview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during submission: " + e.getMessage());
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> getOverviewData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
          ) {
        try {
            Map<String, Long> data = service.getActivitiesDataByDateRange(startDate, endDate);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving data: " + e.getMessage());
        }
    }
}
