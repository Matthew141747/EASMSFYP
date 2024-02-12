package com.fypp.Ethics_Management_System.Submission;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class SubmissionSpecifications {

    public static Specification<Submission> withFaculty(String faculty) {
        return (root, query, cb) -> {
            if (faculty == null || faculty.isEmpty()) return null;
            return cb.equal(root.get("faculty"), faculty);
        };
    }

    public static Specification<Submission> withinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null || endDate == null) return null;
            return cb.between(root.get("submissionDate"), startDate, endDate);
        };
    }

    // You can add more specifications here based on other filters
}