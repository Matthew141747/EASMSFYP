package com.fypp.Ethics_Management_System.Submission;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "submissions")
public class Submission {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private LocalDateTime submissionDate;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<File> files = new HashSet<>();

    public void addFile(File file) {
        files.add(file);
        file.setSubmission(this);
    }

    public void removeFile(File file) {
        files.remove(file);
        file.setSubmission(null);
    }
    public Set<File> getFiles() {
        return files;
    }

}