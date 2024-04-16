package com.fypp.Ethics_Management_System.Analytics.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Human_Participants_in_Study")
public class HumanParticipantsInStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submission_id")
    private Long submissionId;

    @Column(name = "working_with_vulnerable_person")
    private Boolean workingWithVulnerablePerson;

    @Column(name = "staff_clinical_setting")
    private Boolean staffClinicalSetting;

    @Column(name = "basic_knowledge_of_english")
    private Boolean basicKnowledgeOfEnglish;

    @Column(name = "under_age_of_18")
    private Boolean underAgeOfEighteen;

    @Column(name = "adults_with_learning_difficulties")
    private Boolean adultsWithLearningDifficulties;

    @Column(name = "adult_patients")
    private Boolean adultPatients;

    @Column(name = "relatives_of_ill_people")
    private Boolean relativesOfIllPeople;

    @Column(name = "adults_with_psychological_impairments")
    private Boolean adultsWithPsychologicalImpairments;

    @Column(name = "adults_under_protection")
    private Boolean adultsUnderProtection;

    @Column(name = "hospital_gp_patients")
    private Boolean hospitalGpPatients;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    public HumanParticipantsInStudy() {
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

    public Boolean getWorkingWithVulnerablePerson() {
        return workingWithVulnerablePerson;
    }

    public void setWorkingWithVulnerablePerson(Boolean workingWithVulnerablePerson) {
        this.workingWithVulnerablePerson = workingWithVulnerablePerson;
    }

    public Boolean getStaffClinicalSetting() {
        return staffClinicalSetting;
    }

    public void setStaffClinicalSetting(Boolean staffClinicalSetting) {
        this.staffClinicalSetting = staffClinicalSetting;
    }

    public Boolean getBasicKnowledgeOfEnglish() {
        return basicKnowledgeOfEnglish;
    }

    public void setBasicKnowledgeOfEnglish(Boolean basicKnowledgeOfEnglish) {
        this.basicKnowledgeOfEnglish = basicKnowledgeOfEnglish;
    }

    public Boolean getUnderAgeOfEighteen() {
        return underAgeOfEighteen;
    }

    public void setUnderAgeOfEighteen(Boolean underAgeOfEighteen) {
        this.underAgeOfEighteen = underAgeOfEighteen;
    }

    public Boolean getAdultsWithLearningDifficulties() {
        return adultsWithLearningDifficulties;
    }

    public void setAdultsWithLearningDifficulties(Boolean adultsWithLearningDifficulties) {
        this.adultsWithLearningDifficulties = adultsWithLearningDifficulties;
    }

    public Boolean getAdultPatients() {
        return adultPatients;
    }

    public void setAdultPatients(Boolean adultPatients) {
        this.adultPatients = adultPatients;
    }

    public Boolean getRelativesOfIllPeople() {
        return relativesOfIllPeople;
    }

    public void setRelativesOfIllPeople(Boolean relativesOfIllPeople) {
        this.relativesOfIllPeople = relativesOfIllPeople;
    }

    public Boolean getAdultsWithPsychologicalImpairments() {
        return adultsWithPsychologicalImpairments;
    }

    public void setAdultsWithPsychologicalImpairments(Boolean adultsWithPsychologicalImpairments) {
        this.adultsWithPsychologicalImpairments = adultsWithPsychologicalImpairments;
    }

    public Boolean getAdultsUnderProtection() {
        return adultsUnderProtection;
    }

    public void setAdultsUnderProtection(Boolean adultsUnderProtection) {
        this.adultsUnderProtection = adultsUnderProtection;
    }

    public Boolean getHospitalGpPatients() {
        return hospitalGpPatients;
    }

    public void setHospitalGpPatients(Boolean hospitalGpPatients) {
        this.hospitalGpPatients = hospitalGpPatients;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }
}
