package com.fypp.Ethics_Management_System.Parse;

import java.util.Optional;
import java.util.logging.Logger;

public class ApplicationValidator {

    private static final Logger LOGGER = Logger.getLogger(ApplicationValidator.class.getName());

    public static ValidationResults validateApplication(ExpeditedEthicsApplication application) {
        System.out.println("Starting validation of application");

        boolean signaturesPresent = checkSignatures(Optional.ofNullable(application.getDeclaration()));
        boolean correctDocument = checkDocumentUse(Optional.ofNullable(application.getSubjectMatterDetails()), Optional.ofNullable(application.getStudyProcedures()));
        boolean allInformationSheetsPresent = validateInformationSheets(application);
        boolean allConsentSheetsPresent = validateConsentSheets(application);

        System.out.println("Validation completed. Signatures present: " + signaturesPresent + ", Correct document: " + correctDocument +
                ", All Information Sheets present: " + allInformationSheetsPresent + ", All Consent Sheets present: " + allConsentSheetsPresent);

        return new ValidationResults(signaturesPresent, correctDocument, allInformationSheetsPresent, allConsentSheetsPresent);
    }

    private static boolean checkSignatures(Optional<ExpeditedEthicsApplication.Declaration> optionalDeclaration) {
        System.out.println("Checking signatures");

        // Check if declaration is present
        return optionalDeclaration.map(declaration -> {
                    // Now you can safely assume that declaration is not null
                    /*boolean applicantSignature1Valid = Optional.ofNullable(declaration.getApplicantSignature1())
                            .map(signature -> signature.getSignatureId() != null && !signature.getSignatureId().trim().isEmpty())
                            .orElse(true);*/
                    boolean applicantSignature2Valid = Optional.ofNullable(declaration.getApplicantSignature2())
                            .map(signature -> signature.getSignatureId() != null && !signature.getSignatureId().trim().isEmpty())
                            .orElse(false);
                    boolean supervisorSignatureValid = Optional.ofNullable(declaration.getSupervisorSignature())
                            .map(signature -> signature.getSignatureId() != null && !signature.getSignatureId().trim().isEmpty())
                            .orElse(false);

                    //System.out.println("Signatures validation results - Applicant 1: " + applicantSignature1Valid + ", Applicant 2: " + applicantSignature2Valid + ", Supervisor: " + supervisorSignatureValid);
                    System.out.println("Signatures validation results - Applicant 2: "  + applicantSignature2Valid + ", Supervisor: " + supervisorSignatureValid);

                    // If all signatures are valid (not null and not empty), return true
                    return /*applicantSignature1Valid &&*/ applicantSignature2Valid && supervisorSignatureValid;
                })
                .orElse(false); // If the optionalDeclaration is not present, return false
    }

    private static boolean checkDocumentUse(Optional<ExpeditedEthicsApplication.SubjectMatterDetails> optionalSubjectMatterDetails, Optional<ExpeditedEthicsApplication.StudyProcedures> optionalStudyProcedures) {
        System.out.println("Checking document use");

        boolean result = optionalSubjectMatterDetails.map(details -> {
            System.out.println("Checking sensitive topics");
            return details.getSensitiveTopics().values().stream().noneMatch(Boolean::booleanValue);
        }).orElse(false) &&
                optionalStudyProcedures.map(procedures -> {
                    System.out.println("Checking study procedures");
                    return procedures.getProceduresInvolved().values().stream().noneMatch(Boolean::booleanValue);
                }).orElse(false);

        System.out.println("Document use check result: " + result);
        return result;
    }

    private static boolean validateInformationSheets(ExpeditedEthicsApplication application) {
        int expectedCount = application.getResearchProjectInfo().getResearchTypes().values().stream()
                .filter(Boolean::booleanValue)
                .mapToInt(x -> 1)
                .sum();
        return application.getDocumentCounts().getInfoSheetCount() >= expectedCount;
    }

    private static boolean validateConsentSheets(ExpeditedEthicsApplication application) {
        int expectedCount = application.getResearchProjectInfo().getResearchTypes().values().stream()
                .filter(Boolean::booleanValue)
                .mapToInt(x -> 1)
                .sum();
        return application.getDocumentCounts().getConsentSheetCount() >= expectedCount;
    }

    public static class ValidationResults {
        private boolean signaturesPresent;
        private boolean correctDocument;

        private boolean allInformationSheetsPresent;
        private boolean allConsentSheetsPresent;

        public ValidationResults(boolean signaturesPresent, boolean correctDocument, boolean allInformationSheetsPresent, boolean allConsentSheetsPresent) {
            this.signaturesPresent = signaturesPresent;
            this.correctDocument = correctDocument;
            this.allInformationSheetsPresent = allInformationSheetsPresent;
            this.allConsentSheetsPresent = allConsentSheetsPresent;
        }
        public boolean isSignaturesPresent() {
            return signaturesPresent;
        }

        public boolean isCorrectDocument() {
            return correctDocument;
        }

        public boolean isAllInformationSheetsPresent() {
            return allInformationSheetsPresent;
        }

        public boolean isAllConsentSheetsPresent() {
            return allConsentSheetsPresent;
        }
    }

}