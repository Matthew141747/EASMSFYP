package com.fypp.Ethics_Management_System.Analytics.Service;

import com.fypp.Ethics_Management_System.Analytics.DTO.ResearchProjectInformationDTO;
import com.fypp.Ethics_Management_System.Analytics.Entity.ResearchProjectInformation;
import com.fypp.Ethics_Management_System.Analytics.Repository.ResearchProjectInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ResearchProjectInformationService {

    private final ResearchProjectInformationRepository repository;

    @Autowired
    public ResearchProjectInformationService(ResearchProjectInformationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResearchProjectInformation saveResearchProjectInformation(ResearchProjectInformationDTO dto) {
        ResearchProjectInformation projectInfo = new ResearchProjectInformation();

        // Setting the fields from the DTO
        projectInfo.setSubmissionId(dto.getSubmissionId());
        projectInfo.setSubmissionDate(dto.getSubmissionDate());
        projectInfo.setParticipantsRecorded(dto.getParticipantsRecorded());
        projectInfo.setRecordingType(dto.getRecordingType());
        projectInfo.setPrototypeDeveloped(dto.getPrototypeDeveloped());
        projectInfo.setPrototypeServiceFramework(dto.getPrototypeServiceFramework());
        projectInfo.setPrototypeDigitalUiApp(dto.getPrototypeDigitalUiApp());
        projectInfo.setPrototypePhysicalArtifact(dto.getPrototypePhysicalArtifact());
        projectInfo.setMinimumParticipants(dto.getMinimumParticipants());
        projectInfo.setMaximumParticipants(dto.getMaximumParticipants());
        projectInfo.setAccommodationForNonParticipants(dto.getAccommodationForNonParticipants());

        return repository.save(projectInfo);
    }

    public Map<String, Long> getProjectInformationDataByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.countProjectInformationByDateRange(startDate, endDate);
    }
}