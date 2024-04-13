package com.fypp.Ethics_Management_System.SubmissionTracking;

import java.io.Serializable;
import java.util.Objects;

public class FolderSubmissionID implements Serializable {

    private Integer folderId;
    private Long submissionId;


    public FolderSubmissionID() {
    }

    public FolderSubmissionID(Integer folderId, Long submissionId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FolderSubmissionID)) return false;
        FolderSubmissionID that = (FolderSubmissionID) o;
        return Objects.equals(getFolderId(), that.getFolderId()) &&
                Objects.equals(getSubmissionId(), that.getSubmissionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFolderId(), getSubmissionId());
    }
}
