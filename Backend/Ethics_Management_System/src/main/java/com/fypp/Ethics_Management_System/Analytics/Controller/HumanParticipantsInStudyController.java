package com.fypp.Ethics_Management_System.Analytics.Controller;

import com.fypp.Ethics_Management_System.Analytics.DTO.HumanParticipantsInStudyDTO;
import com.fypp.Ethics_Management_System.Analytics.Entity.HumanParticipantsInStudy;
import com.fypp.Ethics_Management_System.Analytics.Service.HumanParticipantsInStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;


@RestController
@RequestMapping("/api/analytics/HumanParticipants")
public class HumanParticipantsInStudyController {

    private final HumanParticipantsInStudyService service;

    @Autowired
    public HumanParticipantsInStudyController(HumanParticipantsInStudyService service) {
        this.service = service;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitHumanParticipantsInStudy(@RequestBody HumanParticipantsInStudyDTO participantsDTO) {
        try {
            HumanParticipantsInStudy savedParticipants = service.saveHumanParticipantsInStudy(participantsDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedParticipants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during submission: " + e.getMessage());
        }
    }

    @GetMapping("/data")
    public ResponseEntity<?> getParticipantsData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            Map<String, Long> data = service.getParticipantsDataByDateRange(startDate, endDate);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving data: " + e.getMessage());
        }
    }


}