package com.fypp.Ethics_Management_System.Parse;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

//This Parsing Class deals with sections One to Five, along with the Submitted Documents section.
//Here a different approach is taken given we aim to primarily capture user input from tables in the pdf.
public class ParseOther {
        public static void parseGeneralInformation(String text, ExpeditedEthicsApplication application) {

                String[] lines = text.split("\n");

                String currentSection = "";


                for (String line : lines) {
                    if (line.contains("1 Supervisor and Applicant Details")) {
                        currentSection = "SupervisorApplicantDetails";
                    } else if (line.contains("2 Human Participants in the Study")) {
                        currentSection = "HumanParticipants";
                    } else if (line.contains("3 Subject Matter of the Study")) {
                        currentSection = "SubjectMatter";
                    } else if (line.contains("4 Procedures in the Study")) {
                        currentSection = "StudyProcedures";
                    } else if (line.contains("5 Research Project Information")) {
                        currentSection = "ResearchProjectInfo";
                    } else if (line.contains("6 Submitted Documents with Application")) {
                        currentSection = "SubmittedDocuments";
                    } else if (line.contains("7 Declaration")) {
                        currentSection = "Declaration";
                    }

                    switch (currentSection) {
                        case "SupervisorApplicantDetails":
                            // Parse Supervisor and Applicant Details
                            if (line.contains("Supervisor name")) {
                                application.getSupervisorApplicantDetails().supervisorName = line.split(":")[1].trim();
                            } else if (line.contains("Supervisor email")) {
                                application.getSupervisorApplicantDetails().supervisorEmail = line.split(":")[1].trim();
                            } else if (line.contains("Applicant name")) {
                                application.getSupervisorApplicantDetails().applicantName = line.split(":")[1].trim();
                            } else if (line.contains("ID number")) {
                                application.getSupervisorApplicantDetails().applicantID = line.split(":")[1].trim();
                            } else if (line.contains("Email address")) {
                                application.getSupervisorApplicantDetails().applicantEmail = line.split(":")[1].trim();
                            } else if (line.contains("Working title of study")) {
                                application.getSupervisorApplicantDetails().studyTitle = line.split(":")[1].trim();
                            } else if (line.contains("Period for which approval is sought")) {
                                // application.getSupervisorApplicantDetails().studyPeriod = line.substring(line.indexOf("Start Date:")).trim();
                            } else if (line.contains("Start Date")) {
                                //application.getSupervisorApplicantDetails().startEndDate = line.substring(line.indexOf("Start Date:")).trim();
                            }
                            break;
                        case "HumanParticipants":
                            // Define all possible criteria
                            List<String> allCriteria = Arrays.asList(
                                    "Working with vulnerable person?",
                                    "Any person under the age of 18?",
                                    "Adult patients?",
                                    "Staff within a clinical setting (i.e., HSE staff)",
                                    "Adults with psychological impairments?",
                                    "Adults with learning difficulties?",
                                    "Relatives of ill people (e.g., parents of sick children)",
                                    "Adults under the protection/control/influence of others (e.g., in care/prison)",
                                    "People who may only have a basic knowledge of English?",
                                    "Hospital or GP patients (or HSE members of staff) recruited in medical facility"
                            );

                            // Parse Human Participants in the Study
                            for (String criteria : allCriteria) {
                                if (line.contains(criteria)) {
                                    // Check if the criteria was selected using ()
                                    boolean isSelected = line.contains("");
                                    application.getHumanParticipantsDetails().participantCriteria.put(criteria, isSelected);
                                }
                            }
                            break;

                        case "SubjectMatter":
                            // Initialize the criteria
                            Map<String, Boolean> allCriteriaSM = new LinkedHashMap<>();
                            allCriteriaSM.put("Sensitive personal issues? (e.g., suicide, bereavement, gender identity, sexuality, fertility, abortion, gambling)", false);
                            allCriteriaSM.put("Illegal activities, illicit drug taking, substance abuse or the self reporting of criminal behaviour", false);
                            allCriteriaSM.put("Any act that might diminish self-respect or cause shame, embarrassment or regret", false);
                            allCriteriaSM.put("Research into politically and/or racially/ethnically and/or commercially sensitive areas", false);

                            String concatenatedLine = ""; // To store concatenated lines
                            for (int i = 0; i < lines.length; i++) {
                                String lineB = lines[i];
                                concatenatedLine += " " + lineB.trim(); // Concatenate lines for multiline criteria

                                // Check the next line to avoid missing out the selection mark
                                if (i + 1 < lines.length) {
                                    String nextLine = lines[i + 1];
                                    if (!nextLine.contains("•") && (nextLine.contains("") || nextLine.contains("☐"))) {
                                        // If the next line does not start a new criterion but contains a selection mark, concatenate it
                                        concatenatedLine += " " + nextLine.trim();
                                        i++; // Skip the next line in the outer loop as it's already processed
                                    }
                                }

                                for (Map.Entry<String, Boolean> entry : allCriteriaSM.entrySet()) {
                                    if (concatenatedLine.contains(entry.getKey())) {
                                        boolean isSelected = concatenatedLine.contains("");
                                        // Update the criterion's value
                                        allCriteriaSM.put(entry.getKey(), isSelected);
                                    }
                                }

                                // Reset the concatenatedLine if it contains a criterion key to handle the next criterion properly
                                for (String key : allCriteriaSM.keySet()) {
                                    if (concatenatedLine.contains(key)) {
                                        concatenatedLine = ""; // Reset for the next iteration
                                        break; // Break after resetting since one line won't contain multiple criteria
                                    }
                                }
                            }

                            // Update the application object with the processed criteria
                            for (Map.Entry<String, Boolean> entry : allCriteriaSM.entrySet()) {
                                application.getSubjectMatterDetails().sensitiveTopics.put(entry.getKey(), entry.getValue());
                            }
                            break;

                        case "StudyProcedures":
                            // Initialize the criteria for the "Procedures in the Study" section
                            Map<String, Boolean> allCriteriaSP = new LinkedHashMap<>();
                            allCriteriaSP.put("Use of personal records without consent", false);
                            allCriteriaSP.put("Deception of participants", false);
                            allCriteriaSP.put("The offer of large inducements to participate", false);
                            allCriteriaSP.put("Audio or visual recording without consent", false);
                            allCriteriaSP.put("Invasive physical interventions or treatments", false);
                            allCriteriaSP.put("Research that might put researchers or participants at risk", false);
                            allCriteriaSP.put("Storage of data for less than 7 years", false);

                            String concatenatedLineSP = ""; // To store concatenated lines for multi-line criteria
                            for (int i = 0; i < lines.length; i++) {
                                String lineSP = lines[i];
                                concatenatedLineSP += " " + lineSP.trim(); // Concatenate lines for multiline criteria

                                // Check the next line to avoid missing the selection mark
                                if (i + 1 < lines.length) {
                                    String nextLine = lines[i + 1];
                                    if (!nextLine.contains("•") && (nextLine.contains("") || nextLine.contains("☐"))) {
                                        concatenatedLineSP += " " + nextLine.trim();
                                        i++; // Skip the next line in the outer loop as it's already processed
                                    }
                                }

                                for (Map.Entry<String, Boolean> entry : allCriteriaSP.entrySet()) {
                                    if (concatenatedLineSP.contains(entry.getKey())) {
                                        boolean isSelected = concatenatedLineSP.contains("");
                                        // Update the criterion's value
                                        allCriteriaSP.put(entry.getKey(), isSelected);
                                    }
                                }

                                // Reset the concatenatedLine if it contains a criterion key to handle the next criterion properly
                                for (String key : allCriteriaSP.keySet()) {
                                    if (concatenatedLineSP.contains(key)) {
                                        concatenatedLineSP = ""; // Reset for the next iteration
                                        break; // Break after resetting since one line won't contain multiple criteria
                                    }
                                }
                            }

                            // Update the application object with the processed criteria
                            for (Map.Entry<String, Boolean> entry : allCriteriaSP.entrySet()) {
                                application.getStudyProcedures().proceduresInvolved.put(entry.getKey(), entry.getValue());
                            }
                            break;


                        case "SubmittedDocuments":
                            // Initialize a map with all document names as keys and false as the default value
                            Map<String, Boolean> submittedDocs = new LinkedHashMap<>();
                            submittedDocs.put("Cover Letter or Study Brochure", false);
                            submittedDocs.put("Recruitment letters, Emails, Social Media text etc.", false);
                            submittedDocs.put("Participant Information Sheet", false);
                            submittedDocs.put("Participant Informed Consent Form", false);
                            submittedDocs.put("Link to Info Sheet & Consent Form", false);
                            submittedDocs.put("List of Survey/Interview Questions attached", false);
                            submittedDocs.put("Link to Survey", false);
                            submittedDocs.put("Parent/Guardian Information Sheet", false);
                            submittedDocs.put("Parent/Guardian Informed Consent Form", false);
                            submittedDocs.put("School Principal Information Sheet", false);
                            submittedDocs.put("School Principal Informed Consent Form", false);
                            submittedDocs.put("Teacher Information Sheet", false);
                            submittedDocs.put("Teacher Consent Form", false);
                            submittedDocs.put("Child Protection Form", false);

                            submittedDocs.keySet().forEach(doc -> {
                                if (line.contains(doc)) {
                                    boolean isProvided = line.endsWith("");
                                    submittedDocs.put(doc, isProvided);
                                }
                            });

                            // Update the application object with the processed documents
                            application.getSubmittedDocuments().documentsProvided = submittedDocs;
                            break;

                    }
                }
                //application.printApplicationDetails();
                //System.out.println(application.getHumanParticipantsDetails());
                //System.out.println(application.getSupervisorApplicantDetails());

        }

    public static void parseDocumentCounts(String text, ExpeditedEthicsApplication application) {
        String[] lines = text.split("\n");
        Pattern infoSheetPattern = Pattern.compile("^\\s*Information Sheet\\b", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
       // Pattern consentSheetPattern = Pattern.compile("Consent Form", Pattern.CASE_INSENSITIVE);
        Pattern consentSheetPattern = Pattern.compile("^\\s*Consent Form\\b", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

        for (String line : lines) {
            if (infoSheetPattern.matcher(line).find()) {
                application.getDocumentCounts().incrementInfoSheetCount();
            }
            if (consentSheetPattern.matcher(line).find()) {
                application.getDocumentCounts().incrementConsentSheetCount();
            }
        }
    }

}
