package com.fypp.Ethics_Management_System.Analytics.Repository.Controller;

import com.fypp.Ethics_Management_System.Analytics.DTO.ResearchProjectInformationDTO;
import com.fypp.Ethics_Management_System.Analytics.Entity.ResearchProjectInformation;
import com.fypp.Ethics_Management_System.Analytics.Service.ResearchProjectInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics/ResearchProjectInformation")
public class ResearchProjectInformationController {

    private final ResearchProjectInformationService service;

    @Autowired
    public ResearchProjectInformationController(ResearchProjectInformationService service) {
        this.service = service;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitResearchProjectInformation(@RequestBody ResearchProjectInformationDTO dto) {
        try {
            ResearchProjectInformation savedProjectInformation = service.saveResearchProjectInformation(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProjectInformation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during submission: " + e.getMessage());
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> getProjectInformationData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            Map<String, Long> data = service.getProjectInformationDataByDateRange(startDate, endDate);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving data: " + e.getMessage());
        }
    }

}
