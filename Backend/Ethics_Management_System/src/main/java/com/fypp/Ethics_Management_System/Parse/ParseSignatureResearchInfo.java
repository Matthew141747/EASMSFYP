package com.fypp.Ethics_Management_System.Parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ParseSignatureResearchInfo {
    public static void parseResearchProjectInfo(String text, ExpeditedEthicsApplication application) {
        //ExpeditedEthicsApplication.ResearchProjectInfo info = new ExpeditedEthicsApplication.ResearchProjectInfo();
        ExpeditedEthicsApplication.ResearchProjectInfo info = application.getResearchProjectInfo();

        // Pattern for identifying checkbox selections and their respective labels
        Pattern checkboxPattern = Pattern.compile("(Survey|Interview|Workshop|Prototype Testing|Biological Sample Acquisition|Data Acquisition|Field Testing) – (.*?)\\s*(|☐)");
        Matcher checkboxMatcher = checkboxPattern.matcher(text);

        while (checkboxMatcher.find()) {
            String key = checkboxMatcher.group(1) + " – " + checkboxMatcher.group(2);
            boolean value = "".equals(checkboxMatcher.group(3));
            info.getResearchTypes().put(key, value);
        }

        // Extract project overview
        String overviewPattern = "ii\\. Give an overview of the research project \\(Mandatory to fill – 300 words maximum\\)(.*?)5b";
        info.setProjectOverview(extractSection(text, overviewPattern));

        // Extract recording details
        boolean willRecord = text.contains("i. Will the participants be recorded?   Yes  ");
        info.getRecordingDetails().setWillParticipantsBeRecorded(willRecord);
        if (willRecord) {
            info.getRecordingDetails().setVideoRecording(text.contains("will the recordings be  Video  "));
            info.getRecordingDetails().setAudioRecording(text.contains("and/or    Audio   "));
            info.getRecordingDetails().setRecordingJustification(extractSection(text, "iii\\. If YES in \\(i\\), then explain why Video and/or Audio recording is required\\?(.*?)5c"));
        }

        // Extract prototype details
        boolean willDevelopPrototype = text.contains("i. Will a prototype be developed?     Yes  ");
        info.getPrototypeDetails().setWillPrototypeBeDeveloped(willDevelopPrototype);
        if (willDevelopPrototype) {
            // This regex pattern assumes the prototype details follow a predictable format. Adjust as necessary.
            Pattern prototypePattern = Pattern.compile("(Service/Framework|Digital UI/App|Physical artifact)\\s*(|☐)");
            Matcher prototypeMatcher = prototypePattern.matcher(text);
            while (prototypeMatcher.find()) {
                String key = prototypeMatcher.group(1);
                boolean value = "".equals(prototypeMatcher.group(2));
                info.getPrototypeDetails().getPrototypeTypes().put(key, value);
            }
            info.getPrototypeDetails().setPrototypeRationale(extractSection(text, "iii\\. If YES in ANY box in \\(i\\), please clarify the rationale behind your choice, describing what format the prototype takes, what it does, and how it will be used and assessed \\(Max. 100 words\\)(.*?)5d"));
        }

        // Extract participant numbers
        Pattern participantNumberPattern = Pattern.compile("Minimum \\[(\\d+)\\]\\s*Maximum \\[(\\d+)\\]");
        Matcher participantMatcher = participantNumberPattern.matcher(text);
        if (participantMatcher.find()) {
            int minParticipants = Integer.parseInt(participantMatcher.group(1));
            int maxParticipants = Integer.parseInt(participantMatcher.group(2));
            info.setMinimumParticipants(minParticipants);
            info.setMaximumParticipants(maxParticipants);
        }
        info.setParticipantJustification(extractSection(text, "Justification \\(Max. 50 words\\): (.*?)5e"));

        // Extract how participants will be approached (5e)
        String approachPattern = "5e\\s*How do you plan to gain access to /contact/approach potential participants\\?(.*?)5f";
        info.setAccessMethod(extractSection(text, approachPattern));

        // Extract inclusion/exclusion criteria (5f)
        String inclusionPattern = "Inclusion Criteria: (.*?)Exclusion Criteria:";
        String exclusionPattern = "Exclusion Criteria: (.*?)(5g|\\z)"; // assuming section 5g follows or it's the end of the document
        String inclusionCriteria = extractSection(text, inclusionPattern);
        String exclusionCriteria = extractSection(text, exclusionPattern);
        info.getParticipantDetails().getInclusionExclusionCriteria().setInclusionCriteria(inclusionCriteria);
        info.getParticipantDetails().getInclusionExclusionCriteria().setExclusionCriteria(exclusionCriteria);

        // Extract data storage details (5l)
        boolean softCopyOnline = text.contains("i. Soft Copy/Online");
        boolean hardCopyPhysical = text.contains("ii. Hard Copy/Physical");
        String onlineStorageDetailsPattern = "i. Soft Copy/Online(.*?)(ii. Hard Copy/Physical|\\z)";
        String physicalStorageLocationPattern = "ii. Hard Copy/Physical(.*?)(\\z|6 Submitted)";
        info.getDataStorageDetails().setSoftCopyOnline(softCopyOnline);
        info.getDataStorageDetails().setHardCopyPhysical(hardCopyPhysical);
        info.getDataStorageDetails().setOnlineStorageDetails(extractSection(text, onlineStorageDetailsPattern));
        info.getDataStorageDetails().setPhysicalStorageLocation(extractSection(text, physicalStorageLocationPattern));

        // Extract Participation Exception Arrangements (5g)
        String participationExceptionPattern = "5g.*?\\s*(Yes  ☐        No             N/A ☐)?(.*?)5h";
        info.setParticipationExceptionArrangements(extractSection(text, participationExceptionPattern));

        // Extract Additional Vulnerability of Participants (5h)
        String vulnerabilityPattern = "5h\\s*(.*?)5i";
        info.setVulnerabilityOfParticipantsAdditional(extractSection(text, vulnerabilityPattern));

        // Extract Study Location (5i)
        String locationPattern = "5i\\s*Where will the study take place\\?\\s*(.*?)5j";
        info.setStudyLocation(extractSection(text, locationPattern));

        // Extract Anonymity and Confidentiality Arrangements (5j)
        String anonymityPattern = "5j\\s*What arrangements have you made for anonymity and confidentiality\\?\\s*(.*?)5k";
        info.setAnonymityAndConfidentialityArrangements(extractSection(text, anonymityPattern));

        // Extract Physical Safety Issues (5k)
        String safetyIssuesPattern = "5k\\s*What are the physical safety issues \\(if any\\) arising from this study, and how will\\s*you deal with them\\?\\s*(.*?)5l";
        info.setPhysicalSafetyIssues(extractSection(text, safetyIssuesPattern));


        application.setResearchProjectInfo(info);
        // printResearchProjectInfo(application);
        //printSignatures(application);
    }

    private static String extractSection(String text, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find() && matcher.group(1) != null) {
            return matcher.group(1).trim();
        }
        return "";
    }

    public static void parseSignatures(String text, ExpeditedEthicsApplication application) {
        // Initialize Declaration object
        ExpeditedEthicsApplication.Declaration declaration = new ExpeditedEthicsApplication.Declaration();

        // Define regex patterns for the signatures

        Pattern applicantPattern = Pattern.compile("Applicant:\\s*Recoverable Signature\\s*X (.+?)\\s*Signed by: (.+?)\\s*(?=Date:)", Pattern.DOTALL);

        Pattern supervisorPattern = Pattern.compile(
                "Principal\\s+Investigator\\*?:\\s*" + // Allow more flexible spacing around "Principal Investigator*:"
                        "Recoverable\\s+Signature\\s*X\\s*" + // Flexible handling of spacing around "Recoverable Signature X"
                        "(.+?)\\s*" + // Capture the name with minimal matching, flexible trailing spaces
                        "Principal\\s+Investigator\\s+/\\s+Supervisor\\s+Signature\\s*" + // More flexible spacing within this title
                        "Signed\\s+by:\\s*(.+?)\\s*", // Capture the signature ID, flexible leading/trailing spaces
                Pattern.DOTALL);


        Matcher applicantMatcher = applicantPattern.matcher(text);
        Matcher supervisorMatcher = supervisorPattern.matcher(text);

        // Extract and set the applicant signatures
        while (applicantMatcher.find()) {
            ExpeditedEthicsApplication.Declaration.Signature signature = new ExpeditedEthicsApplication.Declaration.Signature();
            signature.setName(applicantMatcher.group(1).trim());
            signature.setSignatureId(applicantMatcher.group(2).trim());
            signature.setRole("Applicant");

            //System.out.println("LOOK HERE Applicant Signature Parsed: " + signature.getName() + ", ID: " + signature.getSignatureId());

            // Just to handle the logic for one or two applicant signatures, I use a simple if-else structure
            if (declaration.getApplicantSignature1() == null) {
                declaration.setApplicantSignature1(signature);
            } else {
                declaration.setApplicantSignature2(signature);
            }
        }

        // Extract and set the supervisor signature
        if (supervisorMatcher.find()) {
            ExpeditedEthicsApplication.Declaration.Signature supervisorSignature = new ExpeditedEthicsApplication.Declaration.Signature();
            supervisorSignature.setName(supervisorMatcher.group(1).trim());
            supervisorSignature.setSignatureId(supervisorMatcher.group(2).trim());
            supervisorSignature.setRole("Supervisor");

            //System.out.println("LOOK HERE Supervisor Signature Parsed: " + supervisorSignature.getName() + ", ID: " + supervisorSignature.getSignatureId());

            declaration.setSupervisorSignature(supervisorSignature);
        }

        // Set the declaration object in the application
        application.setDeclaration(declaration);

        //printSignatures(application);
    }

    public static void printResearchProjectInfo(ExpeditedEthicsApplication application) {

        ExpeditedEthicsApplication.ResearchProjectInfo projectInfo = application.getResearchProjectInfo();
        System.out.println("---- Research Project Information ----");
        System.out.println("Project Overview: " + projectInfo.getProjectOverview());

        System.out.println("\n---- Research Types ----");
        projectInfo.getResearchTypes().forEach((key, value) -> System.out.println(key + ": " + (value ? "Yes" : "No")));

        System.out.println("\n---- Recording Details ----");
        System.out.println("Will Participants be Recorded: " + projectInfo.getRecordingDetails().isWillParticipantsBeRecorded());
        System.out.println("Video Recording: " + projectInfo.getRecordingDetails().isVideoRecording());
        System.out.println("Audio Recording: " + projectInfo.getRecordingDetails().isAudioRecording());
        System.out.println("Recording Justification: " + projectInfo.getRecordingDetails().getRecordingJustification());

        System.out.println("\n---- Prototype Details ----");
        System.out.println("Will Prototype be Developed: " + projectInfo.getPrototypeDetails().isWillPrototypeBeDeveloped());
        System.out.println("Prototype Rationale: " + projectInfo.getPrototypeDetails().getPrototypeRationale());
        System.out.println("Prototype Types:");
        projectInfo.getPrototypeDetails().getPrototypeTypes().forEach((type, developed) -> System.out.println(type + ": " + (developed ? "Yes" : "No")));

        System.out.println("\n---- Participant Details ----");
        System.out.println("Inclusion Criteria: " + projectInfo.getParticipantDetails().getInclusionExclusionCriteria().getInclusionCriteria());
        System.out.println("Exclusion Criteria: " + projectInfo.getParticipantDetails().getInclusionExclusionCriteria().getExclusionCriteria());

        System.out.println("\n---- Participant Numbers ----");
        System.out.println("Minimum Participants: " + projectInfo.getMinimumParticipants());
        System.out.println("Maximum Participants: " + projectInfo.getMaximumParticipants());
        System.out.println("Participant Justification: " + projectInfo.getParticipantJustification());

        System.out.println("\n---- Access Method ----");
        System.out.println("Access Method: " + projectInfo.getAccessMethod());

        System.out.println("\n---- Additional Details ----");
        System.out.println("Participation Exception Arrangements: " + projectInfo.getParticipationExceptionArrangements());
        System.out.println("Accommodation for Non-Participants: " + projectInfo.isAccommodationForNonParticipants());
        System.out.println("Additional Vulnerability of Participants: " + projectInfo.getVulnerabilityOfParticipantsAdditional());
        System.out.println("Study Location: " + projectInfo.getStudyLocation());
        System.out.println("Anonymity and Confidentiality Arrangements: " + projectInfo.getAnonymityAndConfidentialityArrangements());
        System.out.println("Physical Safety Issues: " + projectInfo.getPhysicalSafetyIssues());

        System.out.println("\n---- Data Storage Details ----");
        System.out.println("Soft Copy/Online: " + projectInfo.getDataStorageDetails().isSoftCopyOnline());
        System.out.println("Online Storage Details: " + projectInfo.getDataStorageDetails().getOnlineStorageDetails());
        System.out.println("Hard Copy/Physical: " + projectInfo.getDataStorageDetails().isHardCopyPhysical());
        System.out.println("Physical Storage Location: " + projectInfo.getDataStorageDetails().getPhysicalStorageLocation());



        // Print the newly parsed details
        System.out.println("\n---- Participation Exception Arrangements ----");
        System.out.println("Arrangements: " + projectInfo.getParticipationExceptionArrangements());

        System.out.println("\n---- Additional Vulnerability of Participants ----");
        System.out.println("Vulnerability Details: " + projectInfo.getVulnerabilityOfParticipantsAdditional());

        System.out.println("\n---- Study Location ----");
        System.out.println("Location: " + projectInfo.getStudyLocation());

        System.out.println("\n---- Anonymity and Confidentiality Arrangements ----");
        System.out.println("Arrangements: " + projectInfo.getAnonymityAndConfidentialityArrangements());

        System.out.println("\n---- Physical Safety Issues ----");
        System.out.println("Safety Issues: " + projectInfo.getPhysicalSafetyIssues());

    }



    public static void printSignatures(ExpeditedEthicsApplication application) {

        System.out.println("Print Signatures called");
        ExpeditedEthicsApplication.Declaration declaration = application.getDeclaration();
        if (declaration != null) {
            System.out.println("---- Signature Details ----");

            // Print Applicant Signature 1
            if (declaration.getApplicantSignature1() != null) {
                System.out.println("Applicant Signature 1:");
                System.out.println("Name: " + declaration.getApplicantSignature1().getName());
                System.out.println("Role: " + declaration.getApplicantSignature1().getRole());
                System.out.println("Signature ID: " + declaration.getApplicantSignature1().getSignatureId());
                System.out.println();
            }

            // Print Applicant Signature 2
            if (declaration.getApplicantSignature2() != null) {
                System.out.println("Applicant Signature 2:");
                System.out.println("Name: " + declaration.getApplicantSignature2().getName());
                System.out.println("Role: " + declaration.getApplicantSignature2().getRole());
                System.out.println("Signature ID: " + declaration.getApplicantSignature2().getSignatureId());
                System.out.println();
            }

            // Print Supervisor Signature
            if (declaration.getSupervisorSignature() != null) {
                System.out.println("Supervisor Signature:");
                System.out.println("Name: " + declaration.getSupervisorSignature().getName());
                System.out.println("Role: " + declaration.getSupervisorSignature().getRole());
                System.out.println("Signature ID: " + declaration.getSupervisorSignature().getSignatureId());
                System.out.println();
            }
        } else {
            System.out.println("No signatures found in the declaration.");
        }
    }


}

