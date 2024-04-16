package com.fypp.Ethics_Management_System.Analytics.Service;

import com.fypp.Ethics_Management_System.Analytics.DTO.HumanParticipantsInStudyDTO;
import com.fypp.Ethics_Management_System.Analytics.Entity.HumanParticipantsInStudy;
import com.fypp.Ethics_Management_System.Analytics.Repository.HumanParticipantsInStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HumanParticipantsInStudyService {

    private final HumanParticipantsInStudyRepository repository;

    @Autowired
    public HumanParticipantsInStudyService(HumanParticipantsInStudyRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public HumanParticipantsInStudy saveHumanParticipantsInStudy(HumanParticipantsInStudyDTO participantsDTO) {

        HumanParticipantsInStudy participants = new HumanParticipantsInStudy();

        Map<String, Boolean> criteria = participantsDTO.getParticipantCriteria();

        participants.setWorkingWithVulnerablePerson(criteria.get("Working with vulnerable person?"));
        participants.setStaffClinicalSetting(criteria.get("Staff within a clinical setting (i.e., HSE staff)"));
        participants.setBasicKnowledgeOfEnglish(criteria.get("People who may only have a basic knowledge of English?"));
        participants.setUnderAgeOfEighteen(criteria.get("Any person under the age of 18?"));
        participants.setAdultsWithLearningDifficulties(criteria.get("Adults with learning difficulties?"));
        participants.setAdultPatients(criteria.get("Adult patients?"));
        participants.setRelativesOfIllPeople(criteria.get("Relatives of ill people (e.g., parents of sick children)"));
        participants.setAdultsWithPsychologicalImpairments(criteria.get("Adults with psychological impairments?"));
        participants.setAdultsUnderProtection(criteria.get("Adults under the protection of others"));
        participants.setHospitalGpPatients(criteria.get("Hospital or GP patients"));

        participants.setSubmissionId(participantsDTO.getSubmissionId());

        participants.setSubmissionDate(participantsDTO.getSubmissionDate()); // Set the date here

        // Save the entity to the database
        return repository.save(participants);
    }

    public Map<String, Long> getParticipantsDataByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = repository.countParticipantsByDateRange(startDate, endDate);
        if (results.isEmpty()) {
            return Collections.emptyMap();
        }
        Object[] result = results.get(0);
        Map<String, Long> data = new HashMap<>();
        data.put("Working with Vulnerable Person", (Long) result[0]);
        data.put("Staff in Clinical Setting", (Long) result[1]);
        data.put("Basic Knowledge of English", (Long) result[2]);
        data.put("Under Age of Eighteen", (Long) result[3]);
        data.put("Adults with Learning Difficulties", (Long) result[4]);
        data.put("Adult Patients", (Long) result[5]);
        data.put("Relatives of Ill People", (Long) result[6]);
        data.put("Adults with Psychological Impairments", (Long) result[7]);
        data.put("Adults Under Protection", (Long) result[8]);
        data.put("Hospital GP Patients", (Long) result[9]);
        return data;
    }

}