package com.fypp.Ethics_Management_System.Analytics.DTO;

import java.time.LocalDateTime;
import java.util.Map;

public class HumanParticipantsInStudyDTO {
    private Map<String, Boolean> participantCriteria;
    private Long submissionId;

    private LocalDateTime submissionDate;

    public Map<String, Boolean> getParticipantCriteria() {
        return participantCriteria;
    }

    public void setParticipantCriteria(Map<String, Boolean> participantCriteria) {
        this.participantCriteria = participantCriteria;
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
