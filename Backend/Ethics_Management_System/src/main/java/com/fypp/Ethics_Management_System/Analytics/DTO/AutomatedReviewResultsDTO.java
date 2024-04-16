package com.fypp.Ethics_Management_System.Analytics.DTO;

public class AutomatedReviewResultsDTO {

    private Long submissionId;
    private Boolean signaturesPresent;
    private Boolean correctFormUsed;
    private Boolean informationSheetsIncluded;
    private Boolean consentSheetsIncluded;

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
