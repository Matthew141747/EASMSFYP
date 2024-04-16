package com.fypp.Ethics_Management_System.Analytics.DTO;

import java.time.LocalDateTime;
import java.util.Map;

public class ResearchProjectInformationDTO {

    private Long submissionId;
    private Boolean participantsRecorded;
    private Integer recordingType;
    private Boolean prototypeDeveloped;
    private Boolean prototypeServiceFramework;
    private Boolean prototypeDigitalUiApp;
    private Boolean prototypePhysicalArtifact;
    private Integer minimumParticipants;
    private Integer maximumParticipants;
    private Integer accommodationForNonParticipants;

    private LocalDateTime submissionDate;


    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Boolean getParticipantsRecorded() {
        return participantsRecorded;
    }

    public void setParticipantsRecorded(Boolean participantsRecorded) {
        this.participantsRecorded = participantsRecorded;
    }

    public Integer getRecordingType() {
        return recordingType;
    }

    public void setRecordingType(Integer recordingType) {
        this.recordingType = recordingType;
    }

    public Boolean getPrototypeDeveloped() {
        return prototypeDeveloped;
    }

    public void setPrototypeDeveloped(Boolean prototypeDeveloped) {
        this.prototypeDeveloped = prototypeDeveloped;
    }

    public Boolean getPrototypeServiceFramework() {
        return prototypeServiceFramework;
    }

    public void setPrototypeServiceFramework(Boolean prototypeServiceFramework) {
        this.prototypeServiceFramework = prototypeServiceFramework;
    }

    public Boolean getPrototypeDigitalUiApp() {
        return prototypeDigitalUiApp;
    }

    public void setPrototypeDigitalUiApp(Boolean prototypeDigitalUiApp) {
        this.prototypeDigitalUiApp = prototypeDigitalUiApp;
    }

    public Boolean getPrototypePhysicalArtifact() {
        return prototypePhysicalArtifact;
    }

    public void setPrototypePhysicalArtifact(Boolean prototypePhysicalArtifact) {
        this.prototypePhysicalArtifact = prototypePhysicalArtifact;
    }

    public Integer getMinimumParticipants() {
        return minimumParticipants;
    }

    public void setMinimumParticipants(Integer minimumParticipants) {
        this.minimumParticipants = minimumParticipants;
    }

    public Integer getMaximumParticipants() {
        return maximumParticipants;
    }

    public void setMaximumParticipants(Integer maximumParticipants) {
        this.maximumParticipants = maximumParticipants;
    }

    public Integer getAccommodationForNonParticipants() {
        return accommodationForNonParticipants;
    }

    public void setAccommodationForNonParticipants(Integer accommodationForNonParticipants) {
        this.accommodationForNonParticipants = accommodationForNonParticipants;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }


}
