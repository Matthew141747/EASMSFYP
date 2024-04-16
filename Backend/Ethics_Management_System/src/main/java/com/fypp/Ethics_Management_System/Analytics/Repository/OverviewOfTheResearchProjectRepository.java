package com.fypp.Ethics_Management_System.Analytics.Repository;

import com.fypp.Ethics_Management_System.Analytics.Entity.OverviewOfTheResearchProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
public interface OverviewOfTheResearchProjectRepository extends JpaRepository<OverviewOfTheResearchProject, Long> {
    @Query("SELECT new map(" +
            "SUM(case when o.surveyPhysicalOnCampus = true then 1 else 0 end) as surveyPhysicalOnCampus, " +
            "SUM(case when o.surveyPhysicalOffCampus = true then 1 else 0 end) as surveyPhysicalOffCampus, " +
            "SUM(case when o.surveyOnline = true then 1 else 0 end) as surveyOnline, " +
            "SUM(case when o.interviewPhysicalOnCampus = true then 1 else 0 end) as interviewPhysicalOnCampus, " +
            "SUM(case when o.interviewPhysicalOffCampus = true then 1 else 0 end) as interviewPhysicalOffCampus, " +
            "SUM(case when o.interviewOnline = true then 1 else 0 end) as interviewOnline, " +
            "SUM(case when o.workshopPhysicalOnCampus = true then 1 else 0 end) as workshopPhysicalOnCampus, " +
            "SUM(case when o.workshopPhysicalOffCampus = true then 1 else 0 end) as workshopPhysicalOffCampus, " +
            "SUM(case when o.workshopOnline = true then 1 else 0 end) as workshopOnline, " +
            "SUM(case when o.prototypeTestingPhysicalOnCampus = true then 1 else 0 end) as prototypeTestingPhysicalOnCampus, " +
            "SUM(case when o.prototypeTestingPhysicalOffCampus = true then 1 else 0 end) as prototypeTestingPhysicalOffCampus, " +
            "SUM(case when o.prototypeTestingOnline = true then 1 else 0 end) as prototypeTestingOnline, " +
            "SUM(case when o.biologicalSampleAcquisition = true then 1 else 0 end) as biologicalSampleAcquisition, " +
            "SUM(case when o.dataAcquisitionPersonal = true then 1 else 0 end) as dataAcquisitionPersonal, " +
            "SUM(case when o.fieldTestingOnsite = true then 1 else 0 end) as fieldTestingOnsite, " +
            "SUM(case when o.other = true then 1 else 0 end) as other " +
            ") " +
            "FROM OverviewOfTheResearchProject o " +
            "WHERE o.submissionDate BETWEEN :startDate AND :endDate")
    Map<String, Long> countActivitiesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
