package com.fypp.Ethics_Management_System.Analytics.Service;

import com.fypp.Ethics_Management_System.Analytics.DTO.AutomatedReviewResultsDTO;
import com.fypp.Ethics_Management_System.Analytics.Entity.AutomatedReviewResults;
import com.fypp.Ethics_Management_System.Analytics.Repository.AutomatedReviewResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutomatedReviewResultsService {

    private final AutomatedReviewResultsRepository repository;

    @Autowired
    public AutomatedReviewResultsService(AutomatedReviewResultsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public AutomatedReviewResults saveAutomatedReviewResults(AutomatedReviewResultsDTO dto) {
        AutomatedReviewResults results = new AutomatedReviewResults();

        results.setSubmissionId(dto.getSubmissionId());
        results.setSignaturesPresent(dto.getSignaturesPresent());
        results.setCorrectFormUsed(dto.getCorrectFormUsed());
        results.setInformationSheetsIncluded(dto.getInformationSheetsIncluded());
        results.setConsentSheetsIncluded(dto.getConsentSheetsIncluded());

        return repository.save(results);
    }
}
