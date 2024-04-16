package com.fypp.Ethics_Management_System.Analytics.Repository;
import com.fypp.Ethics_Management_System.Analytics.Entity.AutomatedReviewResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomatedReviewResultsRepository extends JpaRepository<AutomatedReviewResults, Long> {

}
