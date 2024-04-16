package com.fypp.Ethics_Management_System.Analytics.DTO;

import java.time.LocalDateTime;
import java.util.Map;

public class OverviewOfTheResearchProjectDTO {

    private Map<String, Boolean> researchTypes;
    private Long submissionId;

    private LocalDateTime submissionDate;

    public Map<String, Boolean> getresearchTypes() {
        return researchTypes;
    }

    public void setResearchDetails(Map<String, Boolean> researchDetails) {
        this.researchTypes = researchDetails;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }
}
