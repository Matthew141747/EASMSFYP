package com.fypp.Ethics_Management_System.Parse;

import java.util.HashMap;
import java.util.Map;

public class ExpeditedEthicsApplication {

    public SupervisorApplicantDetails getSupervisorApplicantDetails() {
        return supervisorApplicantDetails;
    }

    public void setSupervisorApplicantDetails(SupervisorApplicantDetails supervisorApplicantDetails) {
        this.supervisorApplicantDetails = supervisorApplicantDetails;
    }

    public HumanParticipantsDetails getHumanParticipantsDetails() {
        return humanParticipantsDetails;
    }

    public void setHumanParticipantsDetails(HumanParticipantsDetails humanParticipantsDetails) {
        this.humanParticipantsDetails = humanParticipantsDetails;
    }

    private SupervisorApplicantDetails supervisorApplicantDetails;
    private HumanParticipantsDetails humanParticipantsDetails;

    public SubjectMatterDetails getSubjectMatterDetails() {
        return subjectMatterDetails;
    }

    public void setSubjectMatterDetails(SubjectMatterDetails subjectMatterDetails) {
        this.subjectMatterDetails = subjectMatterDetails;
    }

    public StudyProcedures getStudyProcedures() {
        return studyProcedures;
    }

    public void setStudyProcedures(StudyProcedures studyProcedures) {
        this.studyProcedures = studyProcedures;
    }

    public ResearchProjectInfo getResearchProjectInfo() {
        return researchProjectInfo;
    }

    public void setResearchProjectInfo(ResearchProjectInfo researchProjectInfo) {
        this.researchProjectInfo = researchProjectInfo;
    }

    public SubmittedDocuments getSubmittedDocuments() {
        return submittedDocuments;
    }

    public void setSubmittedDocument(SubmittedDocuments submittedDocuments){
        this.submittedDocuments = submittedDocuments;
    }

    private SubjectMatterDetails subjectMatterDetails;
    private StudyProcedures studyProcedures;
    private ResearchProjectInfo researchProjectInfo;
    private SubmittedDocuments submittedDocuments;


    // Constructor
    public ExpeditedEthicsApplication() {
        this.supervisorApplicantDetails = new SupervisorApplicantDetails();
        this.humanParticipantsDetails = new HumanParticipantsDetails();
        this.subjectMatterDetails = new SubjectMatterDetails();
        this.studyProcedures = new StudyProcedures();
        this.researchProjectInfo = new ResearchProjectInfo();
        this.submittedDocuments = new SubmittedDocuments();
    }

    // Nested class for Supervisor and Applicant Details
    public static class SupervisorApplicantDetails {
        String supervisorName;
        String supervisorEmail;
        String applicantName;
        String applicantID;
        String applicantEmail;
        String studyTitle;
        String studyPeriod;
        String startEndDate; // Format: StartDate - EndDate
        String coInvestigatorsUl;
        String coInvestigatorsNotUL;

        public String getSupervisorName() {
            return supervisorName;
        }

        public void setSupervisorName(String supervisorName) {
            this.supervisorName = supervisorName;
        }

        public String getSupervisorEmail() {
            return supervisorEmail;
        }

        public void setSupervisorEmail(String supervisorEmail) {
            this.supervisorEmail = supervisorEmail;
        }

        public String getApplicantName() {
            return applicantName;
        }

        public void setApplicantName(String applicantName) {
            this.applicantName = applicantName;
        }

        public String getApplicantID() {
            return applicantID;
        }

        public void setApplicantID(String applicantID) {
            this.applicantID = applicantID;
        }

        public String getApplicantEmail() {
            return applicantEmail;
        }

        public void setApplicantEmail(String applicantEmail) {
            this.applicantEmail = applicantEmail;
        }

        public String getStudyTitle() {
            return studyTitle;
        }

        public void setStudyTitle(String studyTitle) {
            this.studyTitle = studyTitle;
        }

        public String getStudyPeriod() {
            return studyPeriod;
        }

        public void setStudyPeriod(String studyPeriod) {
            this.studyPeriod = studyPeriod;
        }

        public String getStartEndDate() {
            return startEndDate;
        }

        public void setStartEndDate(String startEndDate) {
            this.startEndDate = startEndDate;
        }

        public String getCoInvestigatorsUl() {
            return coInvestigatorsUl;
        }

        public void setCoInvestigatorsUl(String coInvestigatorsUl) {
            this.coInvestigatorsUl = coInvestigatorsUl;
        }

        public String getCoInvestigatorsNotUL() {
            return coInvestigatorsNotUL;
        }

        public void setCoInvestigatorsNotUL(String coInvestigatorsNotUL) {
            this.coInvestigatorsNotUL = coInvestigatorsNotUL;
        }
    }

    // Nested class for Human Participants in the Study
    public static class HumanParticipantsDetails {
        Map<String, Boolean> participantCriteria = new HashMap<>();

        public Map<String, Boolean> getParticipantCriteria() {
            return participantCriteria;
        }

        public void setParticipantCriteria(Map<String, Boolean> participantCriteria) {
            this.participantCriteria = participantCriteria;
        }
    }

    // Class for SubjectMatter of the Study
    public static class SubjectMatterDetails {
        Map<String, Boolean> sensitiveTopics = new HashMap<>();

        public Map<String, Boolean> getSensitiveTopics() {
            return sensitiveTopics;
        }

        public void setSensitiveTopics(Map<String, Boolean> sensitiveTopics) {
            this.sensitiveTopics = sensitiveTopics;
        }
    }

    // Class for Procedures in the Study
    public static class StudyProcedures {
        Map<String, Boolean> proceduresInvolved = new HashMap<>();

        public Map<String, Boolean> getProceduresInvolved() {
            return proceduresInvolved;
        }

        public void setProceduresInvolved(Map<String, Boolean> proceduresInvolved) {
            this.proceduresInvolved = proceduresInvolved;
        }
    }

    // Class for Research Project Information, given its complexity additional nested classes are used to create this
    public static class ResearchProjectInfo {
        private Map<String, Boolean> researchTypes;
        private String projectOverview;
        private RecordingDetails recordingDetails;
        private PrototypeDetails prototypeDetails;
        private ParticipantDetails participantDetails;
        private String participationExceptionArrangements;
        private boolean accommodationForNonParticipants;
        private String vulnerabilityOfParticipantsAdditional;
        private String studyLocation;
        private String anonymityAndConfidentialityArrangements;
        private String physicalSafetyIssues;
        private DataStorageDetails dataStorageDetails;
        private int minimumParticipants;
        private int maximumParticipants;
        private String participantJustification;
        private String accessMethod;

        public ResearchProjectInfo() {
            this.researchTypes = new HashMap<>();
            this.recordingDetails = new RecordingDetails();
            this.prototypeDetails = new PrototypeDetails();
            this.participantDetails = new ParticipantDetails();
            this.dataStorageDetails = new DataStorageDetails();
        }

        // Getters and Setters
        public Map<String, Boolean> getResearchTypes() {
            return researchTypes;
        }

        public void setResearchTypes(Map<String, Boolean> researchTypes) {
            this.researchTypes = researchTypes;
        }

        public String getProjectOverview() {
            return projectOverview;
        }

        public void setProjectOverview(String projectOverview) {
            this.projectOverview = projectOverview;
        }

        public RecordingDetails getRecordingDetails() {
            return recordingDetails;
        }

        public void setRecordingDetails(RecordingDetails recordingDetails) {
            this.recordingDetails = recordingDetails;
        }

        public PrototypeDetails getPrototypeDetails() {
            return prototypeDetails;
        }

        public void setPrototypeDetails(PrototypeDetails prototypeDetails) {
            this.prototypeDetails = prototypeDetails;
        }

        public ParticipantDetails getParticipantDetails() {
            return participantDetails;
        }

        public void setParticipantDetails(ParticipantDetails participantDetails) {
            this.participantDetails = participantDetails;
        }

        public String getParticipationExceptionArrangements() {
            return participationExceptionArrangements;
        }

        public void setParticipationExceptionArrangements(String participationExceptionArrangements) {
            this.participationExceptionArrangements = participationExceptionArrangements;
        }

        public boolean isAccommodationForNonParticipants() {
            return accommodationForNonParticipants;
        }

        public void setAccommodationForNonParticipants(boolean accommodationForNonParticipants) {
            this.accommodationForNonParticipants = accommodationForNonParticipants;
        }

        public String getVulnerabilityOfParticipantsAdditional() {
            return vulnerabilityOfParticipantsAdditional;
        }

        public void setVulnerabilityOfParticipantsAdditional(String vulnerabilityOfParticipantsAdditional) {
            this.vulnerabilityOfParticipantsAdditional = vulnerabilityOfParticipantsAdditional;
        }

        public String getStudyLocation() {
            return studyLocation;
        }

        public void setStudyLocation(String studyLocation) {
            this.studyLocation = studyLocation;
        }

        public String getAnonymityAndConfidentialityArrangements() {
            return anonymityAndConfidentialityArrangements;
        }

        public void setAnonymityAndConfidentialityArrangements(String anonymityAndConfidentialityArrangements) {
            this.anonymityAndConfidentialityArrangements = anonymityAndConfidentialityArrangements;
        }

        public String getPhysicalSafetyIssues() {
            return physicalSafetyIssues;
        }

        public void setPhysicalSafetyIssues(String physicalSafetyIssues) {
            this.physicalSafetyIssues = physicalSafetyIssues;
        }

        public DataStorageDetails getDataStorageDetails() {
            return dataStorageDetails;
        }

        public void setDataStorageDetails(DataStorageDetails dataStorageDetails) {
            this.dataStorageDetails = dataStorageDetails;
        }

        public int getMinimumParticipants() {
            return minimumParticipants;
        }

        public void setMinimumParticipants(int minimumParticipants) {
            this.minimumParticipants = minimumParticipants;
        }

        public int getMaximumParticipants() {
            return maximumParticipants;
        }

        public void setMaximumParticipants(int maximumParticipants) {
            this.maximumParticipants = maximumParticipants;
        }

        public String getParticipantJustification() {
            return participantJustification;
        }

        public void setParticipantJustification(String participantJustification) {
            this.participantJustification = participantJustification;
        }

        public String getAccessMethod() {
            return accessMethod;
        }

        public void setAccessMethod(String accessMethod) {
            this.accessMethod = accessMethod;
        }
    }

    public static class RecordingDetails {
        boolean willParticipantsBeRecorded;
        boolean videoRecording;
        boolean audioRecording;
        String recordingJustification;

        // Getters and setters
        public boolean isWillParticipantsBeRecorded() {
            return willParticipantsBeRecorded;
        }

        public void setWillParticipantsBeRecorded(boolean willParticipantsBeRecorded) {
            this.willParticipantsBeRecorded = willParticipantsBeRecorded;
        }

        public boolean isVideoRecording() {
            return videoRecording;
        }

        public void setVideoRecording(boolean videoRecording) {
            this.videoRecording = videoRecording;
        }

        public boolean isAudioRecording() {
            return audioRecording;
        }

        public void setAudioRecording(boolean audioRecording) {
            this.audioRecording = audioRecording;
        }

        public String getRecordingJustification() {
            return recordingJustification;
        }

        public void setRecordingJustification(String recordingJustification) {
            this.recordingJustification = recordingJustification;
        }
    }

    public static class PrototypeDetails {
        boolean willPrototypeBeDeveloped;
        Map<String, Boolean> prototypeTypes = new HashMap<>();
        String prototypeRationale;

        // Getters and setters
        public boolean isWillPrototypeBeDeveloped() {
            return willPrototypeBeDeveloped;
        }

        public void setWillPrototypeBeDeveloped(boolean willPrototypeBeDeveloped) {
            this.willPrototypeBeDeveloped = willPrototypeBeDeveloped;
        }

        public Map<String, Boolean> getPrototypeTypes() {
            return prototypeTypes;
        }

        public void setPrototypeTypes(Map<String, Boolean> prototypeTypes) {
            this.prototypeTypes = prototypeTypes;
        }

        public String getPrototypeRationale() {
            return prototypeRationale;
        }

        public void setPrototypeRationale(String prototypeRationale) {
            this.prototypeRationale = prototypeRationale;
        }
    }

    public static class ParticipantDetails {
        private InclusionExclusionCriteria inclusionExclusionCriteria;
        private String participationException;

        public ParticipantDetails() {
            this.inclusionExclusionCriteria = new InclusionExclusionCriteria();
        }

        public InclusionExclusionCriteria getInclusionExclusionCriteria() {
            return inclusionExclusionCriteria;
        }

        public void setInclusionExclusionCriteria(InclusionExclusionCriteria inclusionExclusionCriteria) {
            this.inclusionExclusionCriteria = inclusionExclusionCriteria;
        }

        public String getParticipationException() {
            return participationException;
        }

        public void setParticipationException(String participationException) {
            this.participationException = participationException;
        }
    }

    public static class InclusionExclusionCriteria {
        private String inclusionCriteria;
        private String exclusionCriteria;

        public String getInclusionCriteria() {
            return inclusionCriteria;
        }

        public void setInclusionCriteria(String inclusionCriteria) {
            this.inclusionCriteria = inclusionCriteria;
        }

        public String getExclusionCriteria() {
            return exclusionCriteria;
        }

        public void setExclusionCriteria(String exclusionCriteria) {
            this.exclusionCriteria = exclusionCriteria;
        }
    }

    public static class DataStorageDetails {
        private boolean softCopyOnline;
        private String onlineStorageDetails;
        private boolean hardCopyPhysical;
        private String physicalStorageLocation;

        public boolean isSoftCopyOnline() {
            return softCopyOnline;
        }

        public void setSoftCopyOnline(boolean softCopyOnline) {
            this.softCopyOnline = softCopyOnline;
        }

        public String getOnlineStorageDetails() {
            return onlineStorageDetails;
        }

        public void setOnlineStorageDetails(String onlineStorageDetails) {
            this.onlineStorageDetails = onlineStorageDetails;
        }

        public boolean isHardCopyPhysical() {
            return hardCopyPhysical;
        }

        public void setHardCopyPhysical(boolean hardCopyPhysical) {
            this.hardCopyPhysical = hardCopyPhysical;
        }

        public String getPhysicalStorageLocation() {
            return physicalStorageLocation;
        }

        public void setPhysicalStorageLocation(String physicalStorageLocation) {
            this.physicalStorageLocation = physicalStorageLocation;
        }
    }


    public static class SubmittedDocuments {
        Map<String, Boolean> documentsProvided = new HashMap<>();

        public Map<String, Boolean> getDocumentsProvided() {
            return documentsProvided;
        }

        public void setDocumentsProvided(Map<String, Boolean> documentsProvided) {
            this.documentsProvided = documentsProvided;
        }
    }

    private Declaration declaration;

    public Declaration getDeclaration() {
        return declaration;
    }

    public void setDeclaration(Declaration declaration) {
        this.declaration = declaration;
    }


    // Nested Class for Declaration Section, stores signatures found in the pdf.
    public static class Declaration {
        private Signature applicantSignature1;
        private Signature applicantSignature2;
        private Signature supervisorSignature;

        public Declaration() {
            // Initialize signatures
            this.applicantSignature1 = new Signature();
            this.applicantSignature2 = new Signature();
            this.supervisorSignature = new Signature();
        }

        // Getters and Setters
        public Signature getApplicantSignature1() {
            return applicantSignature1;
        }

        public void setApplicantSignature1(Signature applicantSignature1) {
            this.applicantSignature1 = applicantSignature1;
        }

        public Signature getApplicantSignature2() {
            return applicantSignature2;
        }

        public void setApplicantSignature2(Signature applicantSignature2) {
            this.applicantSignature2 = applicantSignature2;
        }

        public Signature getSupervisorSignature() {
            return supervisorSignature;
        }

        public void setSupervisorSignature(Signature supervisorSignature) {
            this.supervisorSignature = supervisorSignature;
        }

        // Nested class for Signature details
        public static class Signature {
            private String name;
            private String role;
            private String signatureId;

            // Getters and Setters
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getSignatureId() {
                return signatureId;
            }

            public void setSignatureId(String signatureId) {
                this.signatureId = signatureId;
            }
        }
    }
    /*
    public void printApplicationDetails() {
        System.out.println("Supervisor and Applicant Details:");
        System.out.println("Supervisor Name: " + supervisorApplicantDetails.supervisorName);
        System.out.println("Supervisor Email: " + supervisorApplicantDetails.supervisorEmail);
        System.out.println("Applicant Name: " + supervisorApplicantDetails.applicantName);
        System.out.println("Applicant ID: " + supervisorApplicantDetails.applicantID);
        System.out.println("Applicant Email: " + supervisorApplicantDetails.applicantEmail);
        System.out.println("Study Title: " + supervisorApplicantDetails.studyTitle);
        System.out.println("Study Period: " + supervisorApplicantDetails.studyPeriod);
        System.out.println("Start-End Date: " + supervisorApplicantDetails.startEndDate);

        System.out.println("\nHuman Participants in the Study:");
        humanParticipantsDetails.participantCriteria.forEach((criteria, isSelected) -> {
            System.out.println(criteria + ": " + (isSelected ? "Yes" : "No"));
        });

        System.out.println("\nSubject Matter in the Study:");
        subjectMatterDetails.sensitiveTopics.forEach((criteria, isSelected) -> {
            System.out.println(criteria + ": " + (isSelected ? "Yes" : "No"));
        });
        System.out.println("\nProcedures in the Study:");
        studyProcedures.proceduresInvolved.forEach((criteria, isSelected) -> {
            System.out.println(criteria + ": " + (isSelected ? "Yes" : "No"));
        });

        System.out.println("\nSubmitted Documents:");
        submittedDocuments.documentsProvided.forEach((criteria, isSelected) -> {
            System.out.println(criteria + ": " + (isSelected ? "Yes" : "No"));
        });

    }*/

}
