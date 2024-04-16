package com.fypp.Ethics_Management_System.Analytics.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Overview_of_the_Research_Project")
public class OverviewOfTheResearchProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "survey_physical_on_campus")
    private Boolean surveyPhysicalOnCampus;

    @Column(name = "survey_physical_off_campus")
    private Boolean surveyPhysicalOffCampus;

    @Column(name = "survey_online")
    private Boolean surveyOnline;

    @Column(name = "interview_physical_on_campus")
    private Boolean interviewPhysicalOnCampus;

    @Column(name = "interview_physical_off_campus")
    private Boolean interviewPhysicalOffCampus;

    @Column(name = "interview_online")
    private Boolean interviewOnline;

    @Column(name = "workshop_physical_on_campus")
    private Boolean workshopPhysicalOnCampus;

    @Column(name = "workshop_physical_off_campus")
    private Boolean workshopPhysicalOffCampus;

    @Column(name = "workshop_online")
    private Boolean workshopOnline;

    @Column(name = "prototype_testing_physical_on_campus")
    private Boolean prototypeTestingPhysicalOnCampus;

    @Column(name = "prototype_testing_physical_off_campus")
    private Boolean prototypeTestingPhysicalOffCampus;

    @Column(name = "prototype_testing_online")
    private Boolean prototypeTestingOnline;

    @Column(name = "biological_sample_acquisition")
    private Boolean biologicalSampleAcquisition;

    @Column(name = "data_acquisition_personal")
    private Boolean dataAcquisitionPersonal;

    @Column(name = "field_testing_onsite")
    private Boolean fieldTestingOnsite;

    @Column(name = "other")
    private Boolean other;

    @Column(name = "submission_id")
    private Long submissionId;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    public OverviewOfTheResearchProject(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSurveyPhysicalOnCampus() {
        return surveyPhysicalOnCampus;
    }

    public void setSurveyPhysicalOnCampus(Boolean surveyPhysicalOnCampus) {
        this.surveyPhysicalOnCampus = surveyPhysicalOnCampus;
    }

    public Boolean getSurveyPhysicalOffCampus() {
        return surveyPhysicalOffCampus;
    }

    public void setSurveyPhysicalOffCampus(Boolean surveyPhysicalOffCampus) {
        this.surveyPhysicalOffCampus = surveyPhysicalOffCampus;
    }

    public Boolean getSurveyOnline() {
        return surveyOnline;
    }

    public void setSurveyOnline(Boolean surveyOnline) {
        this.surveyOnline = surveyOnline;
    }

    public Boolean getInterviewPhysicalOnCampus() {
        return interviewPhysicalOnCampus;
    }

    public void setInterviewPhysicalOnCampus(Boolean interviewPhysicalOnCampus) {
        this.interviewPhysicalOnCampus = interviewPhysicalOnCampus;
    }

    public Boolean getInterviewPhysicalOffCampus() {
        return interviewPhysicalOffCampus;
    }

    public void setInterviewPhysicalOffCampus(Boolean interviewPhysicalOffCampus) {
        this.interviewPhysicalOffCampus = interviewPhysicalOffCampus;
    }

    public Boolean getInterviewOnline() {
        return interviewOnline;
    }

    public void setInterviewOnline(Boolean interviewOnline) {
        this.interviewOnline = interviewOnline;
    }

    public Boolean getWorkshopPhysicalOnCampus() {
        return workshopPhysicalOnCampus;
    }

    public void setWorkshopPhysicalOnCampus(Boolean workshopPhysicalOnCampus) {
        this.workshopPhysicalOnCampus = workshopPhysicalOnCampus;
    }

    public Boolean getWorkshopPhysicalOffCampus() {
        return workshopPhysicalOffCampus;
    }

    public void setWorkshopPhysicalOffCampus(Boolean workshopPhysicalOffCampus) {
        this.workshopPhysicalOffCampus = workshopPhysicalOffCampus;
    }

    public Boolean getWorkshopOnline() {
        return workshopOnline;
    }

    public void setWorkshopOnline(Boolean workshopOnline) {
        this.workshopOnline = workshopOnline;
    }

    public Boolean getPrototypeTestingPhysicalOnCampus() {
        return prototypeTestingPhysicalOnCampus;
    }

    public void setPrototypeTestingPhysicalOnCampus(Boolean prototypeTestingPhysicalOnCampus) {
        this.prototypeTestingPhysicalOnCampus = prototypeTestingPhysicalOnCampus;
    }

    public Boolean getPrototypeTestingPhysicalOffCampus() {
        return prototypeTestingPhysicalOffCampus;
    }

    public void setPrototypeTestingPhysicalOffCampus(Boolean prototypeTestingPhysicalOffCampus) {
        this.prototypeTestingPhysicalOffCampus = prototypeTestingPhysicalOffCampus;
    }

    public Boolean getPrototypeTestingOnline() {
        return prototypeTestingOnline;
    }

    public void setPrototypeTestingOnline(Boolean prototypeTestingOnline) {
        this.prototypeTestingOnline = prototypeTestingOnline;
    }

    public Boolean getBiologicalSampleAcquisition() {
        return biologicalSampleAcquisition;
    }

    public void setBiologicalSampleAcquisition(Boolean biologicalSampleAcquisition) {
        this.biologicalSampleAcquisition = biologicalSampleAcquisition;
    }

    public Boolean getDataAcquisitionPersonal() {
        return dataAcquisitionPersonal;
    }

    public void setDataAcquisitionPersonal(Boolean dataAcquisitionPersonal) {
        this.dataAcquisitionPersonal = dataAcquisitionPersonal;
    }

    public Boolean getFieldTestingOnsite() {
        return fieldTestingOnsite;
    }

    public void setFieldTestingOnsite(Boolean fieldTestingOnsite) {
        this.fieldTestingOnsite = fieldTestingOnsite;
    }

    public Boolean getOther() {
        return other;
    }

    public void setOther(Boolean other) {
        this.other = other;
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
