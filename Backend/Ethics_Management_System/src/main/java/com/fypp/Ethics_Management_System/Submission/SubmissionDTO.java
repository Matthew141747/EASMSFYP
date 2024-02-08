package com.fypp.Ethics_Management_System.Submission;

import java.time.LocalDateTime;
import java.util.List;

public class SubmissionDTO {
    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<FileDTO> getFileMetadataList() {
        return fileMetadataList;
    }

    public void setFileMetadataList(List<FileDTO> fileMetadataList) {
        this.fileMetadataList = fileMetadataList;
    }

    public SubmissionDTO(Long submissionId, int userId, List<FileDTO> fileMetadataList, String department, String faculty, String studentId, LocalDateTime submissionDate) {
        this.submissionId = submissionId;
        this.userId = userId;
        this.fileMetadataList = fileMetadataList;
        this.studentId = studentId;
        this.department = department;
        this.faculty = faculty;
        this.submissionDate = submissionDate;
    }

    private Long submissionId;
    private int userId;
    private List<FileDTO> fileMetadataList;

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    private String faculty;
    private String department;
    private String studentId;
    private LocalDateTime submissionDate;

}
