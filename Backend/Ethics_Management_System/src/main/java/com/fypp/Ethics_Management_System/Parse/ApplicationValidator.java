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

        // Use optionalDeclaration.isPresent() to check if declaration is present
        return optionalDeclaration.map(declaration -> {
                    // Now you can safely assume that declaration is not null
                    boolean applicantSignature1Valid = Optional.ofNullable(declaration.getApplicantSignature1())
                            .map(signature -> signature.getSignatureId() != null && !signature.getSignatureId().trim().isEmpty())
                            .orElse(false);
                    boolean applicantSignature2Valid = Optional.ofNullable(declaration.getApplicantSignature2())
                            .map(signature -> signature.getSignatureId() != null && !signature.getSignatureId().trim().isEmpty())
                            .orElse(false);
                    boolean supervisorSignatureValid = Optional.ofNullable(declaration.getSupervisorSignature())
                            .map(signature -> signature.getSignatureId() != null && !signature.getSignatureId().trim().isEmpty())
                            .orElse(false);

                    System.out.println("Signatures validation results - Applicant 1: " + applicantSignature1Valid + ", Applicant 2: " + applicantSignature2Valid + ", Supervisor: " + supervisorSignatureValid);

                    // If all signatures are valid (not null and not empty), return true
                    return applicantSignature1Valid && applicantSignature2Valid && supervisorSignatureValid;
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