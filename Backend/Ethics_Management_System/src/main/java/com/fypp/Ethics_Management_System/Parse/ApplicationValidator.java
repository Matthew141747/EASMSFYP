package com.fypp.Ethics_Management_System.Parse;

import java.util.Optional;
import java.util.logging.Logger;

public class ApplicationValidator {

    private static final Logger LOGGER = Logger.getLogger(ApplicationValidator.class.getName());

    public static ValidationResults validateApplication(ExpeditedEthicsApplication application) {
        System.out.println("Starting validation of application");

        boolean signaturesPresent = checkSignatures(Optional.ofNullable(application.getDeclaration()));
        boolean correctDocument = checkDocumentUse(Optional.ofNullable(application.getSubjectMatterDetails()), Optional.ofNullable(application.getStudyProcedures()));

        System.out.println("Validation completed. Signatures present: " + signaturesPresent + ", Correct document: " + correctDocument);

        return new ValidationResults(signaturesPresent, correctDocument);
    }

    private static boolean checkSignatures(Optional<ExpeditedEthicsApplication.Declaration> optionalDeclaration) {
        System.out.println("Checking signatures");

        boolean result = optionalDeclaration.map(declaration -> {
            try {
                System.out.println("Checking individual signatures");
                boolean applicantSignature1Valid = Optional.ofNullable(declaration.getApplicantSignature1()).map(signature -> !signature.getSignatureId().isEmpty()).orElse(false);
                boolean applicantSignature2Valid = Optional.ofNullable(declaration.getApplicantSignature2()).map(signature -> !signature.getSignatureId().isEmpty()).orElse(false);
                boolean supervisorSignatureValid = Optional.ofNullable(declaration.getSupervisorSignature()).map(signature -> !signature.getSignatureId().isEmpty()).orElse(false);

                System.out.println("Signatures validation results - Applicant 1: " + applicantSignature1Valid + ", Applicant 2: " + applicantSignature2Valid + ", Supervisor: " + supervisorSignatureValid);

                return applicantSignature1Valid && applicantSignature2Valid && supervisorSignatureValid;
            } catch (Exception e) {
                System.out.println("Exception in checkSignatures: " + e.getMessage());
                e.printStackTrace(); // For detailed stack trace in logs
                return false;
            }
        }).orElse(false);

        System.out.println("Signatures check result: " + result);
        return result;
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

    public static class ValidationResults {
        private boolean signaturesPresent;
        private boolean correctDocument;

        public ValidationResults(boolean signaturesPresent, boolean correctDocument) {
            this.signaturesPresent = signaturesPresent;
            this.correctDocument = correctDocument;
        }

        public boolean areSignaturesPresent() {
            return signaturesPresent;
        }

        public boolean isCorrectDocument() {
            return correctDocument;
        }
    }

}