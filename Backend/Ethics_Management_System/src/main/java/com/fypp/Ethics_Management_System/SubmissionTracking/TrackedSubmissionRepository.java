package com.fypp.Ethics_Management_System.SubmissionTracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackedSubmissionRepository extends JpaRepository<TrackedSubmission, Long> {
    List<TrackedSubmission> findByFacultyUserId(Integer facultyUserId);
    Optional<TrackedSubmission> findBySubmissionIdAndFacultyUserId(Long submissionId, Integer facultyUserId);

    //List<FolderSubmission> findByFolderId(Integer folderId);

}
