package com.fypp.Ethics_Management_System.SubmissionTracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderSubmissionRepository extends JpaRepository<FolderSubmission, FolderSubmissionID> {

    // Methods to find folder submissions by folder or submission id
     List<FolderSubmission> findByFolderId(Integer folderId);

    Optional<FolderSubmission> findByFolderIdAndSubmissionId(Integer folderId, Long submissionId);
    List<FolderSubmission> findBySubmissionId(Long submissionId);

}