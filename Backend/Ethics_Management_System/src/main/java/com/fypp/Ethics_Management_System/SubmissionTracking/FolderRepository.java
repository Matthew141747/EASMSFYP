package com.fypp.Ethics_Management_System.SubmissionTracking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

    // Method to find folders by the faculty user id
    List<Folder> findByFacultyUserId(Integer facultyUserId);


    // find a folder by ID and faculty user ID
    Optional<Folder> findByIdAndFacultyUserId(Integer id, Integer facultyUserId);

}