package com.fypp.Ethics_Management_System.Submission;

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

    public SubmissionDTO(Long submissionId, int userId, List<FileDTO> fileMetadataList) {
        this.submissionId = submissionId;
        this.userId = userId;
        this.fileMetadataList = fileMetadataList;
    }

    private Long submissionId;
    private int userId;
    private List<FileDTO> fileMetadataList;

}
