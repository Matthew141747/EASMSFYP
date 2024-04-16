package com.fypp.Ethics_Management_System.Analytics.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Automated_Review_Results")
public class AutomatedReviewResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submission_id")
    private Long submissionId;

    @Column(name = "signatures_present")
    private Boolean signaturesPresent;

    @Column(name = "correct_form_used")
    private Boolean correctFormUsed;

    @Column(name = "information_sheets_included")
    private Boolean informationSheetsIncluded;

    @Column(name = "consent_sheets_included")
    private Boolean consentSheetsIncluded;

    public AutomatedReviewResults() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Boolean getSignaturesPresent() {
        return signaturesPresent;
    }

    public void setSignaturesPresent(Boolean signaturesPresent) {
        this.signaturesPresent = signaturesPresent;
    }

    public Boolean getCorrectFormUsed() {
        return correctFormUsed;
    }

    public void setCorrectFormUsed(Boolean correctFormUsed) {
        this.correctFormUsed = correctFormUsed;
    }

    public Boolean getInformationSheetsIncluded() {
        return informationSheetsIncluded;
    }

    public void setInformationSheetsIncluded(Boolean informationSheetsIncluded) {
        this.informationSheetsIncluded = informationSheetsIncluded;
    }

    public Boolean getConsentSheetsIncluded() {
        return consentSheetsIncluded;
    }

    public void setConsentSheetsIncluded(Boolean consentSheetsIncluded) {
        this.consentSheetsIncluded = consentSheetsIncluded;
    }
}