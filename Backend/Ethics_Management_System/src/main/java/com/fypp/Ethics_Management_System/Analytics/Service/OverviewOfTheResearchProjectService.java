package com.fypp.Ethics_Management_System.Analytics.Service;

import com.fypp.Ethics_Management_System.Analytics.DTO.OverviewOfTheResearchProjectDTO;
import com.fypp.Ethics_Management_System.Analytics.Entity.OverviewOfTheResearchProject;
import com.fypp.Ethics_Management_System.Analytics.Repository.OverviewOfTheResearchProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class OverviewOfTheResearchProjectService {

    private final OverviewOfTheResearchProjectRepository repository;

    @Autowired
    public OverviewOfTheResearchProjectService(OverviewOfTheResearchProjectRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public OverviewOfTheResearchProject saveOverviewOfTheResearchProject(OverviewOfTheResearchProjectDTO dto) {
        OverviewOfTheResearchProject overview = new OverviewOfTheResearchProject();

        Map<String, Boolean> details  = dto.getresearchTypes();

        // Map the DTO to the entity
        overview.setSurveyPhysicalOnCampus(details.get("Survey – Physical (on UL campus)"));
        overview.setSurveyPhysicalOffCampus(details.get("Survey – Physical (off UL campus)"));
        overview.setSurveyOnline(details.get("Survey – Online (provide link below in part ii)"));
        overview.setInterviewPhysicalOnCampus(details.get("Interview – Physical (on UL campus)"));
        overview.setInterviewPhysicalOffCampus(details.get("Interview – Physical (off UL campus)"));
        overview.setInterviewOnline(details.get("Interview – Online (provide link below in part ii)"));
        overview.setWorkshopPhysicalOnCampus(details.get("Workshop – Physical (on UL campus)"));
        overview.setWorkshopPhysicalOffCampus(details.get("Workshop – Physical (off UL campus)"));
        overview.setWorkshopOnline(details.get("Workshop – Online (provide link below in part ii)"));
        overview.setPrototypeTestingPhysicalOnCampus(details.get("Prototype Testing – Physical (on UL campus)"));
        overview.setPrototypeTestingPhysicalOffCampus(details.get("Prototype Testing – Physical (off UL campus)"));
        overview.setPrototypeTestingOnline(details.get("Prototype Testing – Online (provide link below in part ii)"));
        overview.setBiologicalSampleAcquisition(details.get("Biological Sample Acquisition – blood, urine, tissue etc."));
        overview.setDataAcquisitionPersonal(details.get("Data Acquisition – personal data collection"));
        overview.setFieldTestingOnsite(details.get("Field Testing – onsite testing of a product"));
        overview.setOther(details.get("Other"));

        overview.setSubmissionId(dto.getSubmissionId());

        overview.setSubmissionDate(dto.getSubmissionDate());

        return repository.save(overview);
    }

    public Map<String, Long> getActivitiesDataByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.countActivitiesByDateRange(startDate, endDate);
    }
}
