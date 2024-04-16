package com.fypp.Ethics_Management_System.Analytics.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Research_Project_Information")
public class ResearchProjectInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submission_id")
    private Long submissionId;

    @Column(name = "participants_recorded")
    private Boolean participantsRecorded;

    @Column(name = "recording_type")
    private Integer recordingType;

    @Column(name = "prototype_developed")
    private Boolean prototypeDeveloped;

    @Column(name = "prototype_service_framework")
    private Boolean prototypeServiceFramework;

    @Column(name = "prototype_digital_ui_app")
    private Boolean prototypeDigitalUiApp;

    @Column(name = "prototype_physical_artifact")
    private Boolean prototypePhysicalArtifact;

    @Column(name = "minimum_participants")
    private Integer minimumParticipants;

    @Column(name = "maximum_participants")
    private Integer maximumParticipants;

    @Column(name = "accommodation_for_non_participants")
    private Integer accommodationForNonParticipants;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    public ResearchProjectInformation() {
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
