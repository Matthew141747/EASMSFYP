package com.fypp.Ethics_Management_System.Submission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public interface SubmissionRepository extends JpaRepository<Submission, Long>, JpaSpecificationExecutor<Submission> {

    Page<Submission> findAll(Specification<Submission> spec, Pageable pageable);

    //List<Submission> findAll();

    List<Submission> findByFaculty(String faculty);

    List<Submission> findByDepartment(String department);

    List<Submission> findBySubmissionDate(LocalDate submissionDate);

    List<Submission> findByUserId(int userId);
    //Analytics Page, application volume
    @Query("SELECT s.faculty as faculty, COUNT(s) as volume FROM Submission s WHERE s.submissionDate BETWEEN :startDate AND :endDate GROUP BY s.faculty")
    List<Object[]> countBySubmissionDateBetweenGroupByFaculty(LocalDateTime startDate, LocalDateTime endDate);

    //Submission Dashboard Search
    Page<Submission> findByApplicantNameContaining(String name, Pageable pageable);
    Page<Submission> findByStudentIdContaining(String studentId, Pageable pageable);
    Page<Submission> findByApplicantNameContainingOrStudentIdContaining(String name, String studentId, Pageable pageable);
}