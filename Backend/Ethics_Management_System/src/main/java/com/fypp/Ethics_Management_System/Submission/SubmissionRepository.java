package com.fypp.Ethics_Management_System.Submission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findAll();

    List<Submission> findByFaculty(String faculty);

    List<Submission> findByDepartment(String department);

    List<Submission> findBySubmissionDate(LocalDate submissionDate);

}