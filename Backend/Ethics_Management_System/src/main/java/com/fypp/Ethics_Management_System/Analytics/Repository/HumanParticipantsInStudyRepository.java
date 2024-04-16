package com.fypp.Ethics_Management_System.Analytics.Repository;

import com.fypp.Ethics_Management_System.Analytics.Entity.HumanParticipantsInStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HumanParticipantsInStudyRepository extends JpaRepository<HumanParticipantsInStudy, Long> {

    @Query("SELECT " +
            "SUM(case when h.workingWithVulnerablePerson = true then 1 else 0 end), " +
            "SUM(case when h.staffClinicalSetting = true then 1 else 0 end), " +
            "SUM(case when h.basicKnowledgeOfEnglish = true then 1 else 0 end), " +
            "SUM(case when h.underAgeOfEighteen = true then 1 else 0 end), " +
            "SUM(case when h.adultsWithLearningDifficulties = true then 1 else 0 end), " +
            "SUM(case when h.adultPatients = true then 1 else 0 end), " +
            "SUM(case when h.relativesOfIllPeople = true then 1 else 0 end), " +
            "SUM(case when h.adultsWithPsychologicalImpairments = true then 1 else 0 end), " +
            "SUM(case when h.adultsUnderProtection = true then 1 else 0 end), " +
            "SUM(case when h.hospitalGpPatients = true then 1 else 0 end) " +
            "FROM HumanParticipantsInStudy h " +
            "WHERE h.submissionDate BETWEEN :startDate AND :endDate")
    List<Object[]> countParticipantsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}

