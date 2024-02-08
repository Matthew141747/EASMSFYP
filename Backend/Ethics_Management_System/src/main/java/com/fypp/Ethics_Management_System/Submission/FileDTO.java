package com.fypp.Ethics_Management_System.Submission;

import java.time.LocalDateTime;

public class FileDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private int userId;
    private String filePath;
    private LocalDateTime uploadDate;

    public FileDTO(Long id, String fileName, String fileType, int userId, String filePath, LocalDateTime uploadDate) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.userId = userId;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
