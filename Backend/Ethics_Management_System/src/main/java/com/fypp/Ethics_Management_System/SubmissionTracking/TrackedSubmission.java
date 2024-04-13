package com.fypp.Ethics_Management_System.SubmissionTracking;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tracked_submissions")
public class TrackedSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getFacultyUserId() {
        return facultyUserId;
    }

    public void setFacultyUserId(Integer facultyUserId) {
        this.facultyUserId = facultyUserId;
    }

    public LocalDateTime getTrackedDate() {
        return trackedDate;
    }

    public void setTrackedDate(LocalDateTime trackedDate) {
        this.trackedDate = trackedDate;
    }

    @Column(name = "submission_id", nullable = false)
    private Long submissionId;

    @Column(name = "faculty_user_id", nullable = false)
    private Integer facultyUserId;

    @Column(name = "tracked_date")
    private LocalDateTime trackedDate;

}
