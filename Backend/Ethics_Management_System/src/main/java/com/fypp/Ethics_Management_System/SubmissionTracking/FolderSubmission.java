package com.fypp.Ethics_Management_System.SubmissionTracking;

import jakarta.persistence.*;

@Entity
@Table(name = "folder_submissions")
@IdClass(FolderSubmissionID.class) // Composite primary key class
public class FolderSubmission {

    @Id
    @Column(name = "folder_id")
    private Integer folderId;

    @Id
    @Column(name = "submission_id")
    private Long submissionId;



    public FolderSubmission() {
    }

    public FolderSubmission(Integer folderId, Long submissionId) {
        this.folderId = folderId;
        this.submissionId = submissionId;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }
}
