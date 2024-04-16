package com.fypp.Ethics_Management_System.Analytics.Repository;

import com.fypp.Ethics_Management_System.Analytics.Entity.ResearchProjectInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
public interface ResearchProjectInformationRepository extends JpaRepository<ResearchProjectInformation, Long> {
    @Query("SELECT new map(" +
            "SUM(case when r.participantsRecorded = true then 1 else 0 end) as participantsRecorded, " +
            "SUM(case when r.recordingType = 1 then 1 else 0 end) as audioRecording, " +
            "SUM(case when r.recordingType = 2 then 1 else 0 end) as videoRecording, " +
            "SUM(case when r.recordingType = 3 then 1 else 0 end) as audioVideoRecording, " +
            "SUM(case when r.prototypeDeveloped = true then 1 else 0 end) as prototypeDeveloped, " +
            "SUM(case when r.prototypeServiceFramework = true then 1 else 0 end) as prototypeServiceFramework, " +
            "SUM(case when r.prototypeDigitalUiApp = true then 1 else 0 end) as prototypeDigitalUiApp, " +
            "SUM(case when r.prototypePhysicalArtifact = true then 1 else 0 end) as prototypePhysicalArtifact, " +
            "SUM(case when r.accommodationForNonParticipants = 1 then 1 else 0 end) as accommodationForNonParticipants " +
            ") " +
            "FROM ResearchProjectInformation r " +
            "WHERE r.submissionDate BETWEEN :startDate AND :endDate")
    Map<String, Long> countProjectInformationByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
