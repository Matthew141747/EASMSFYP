package com.fypp.Ethics_Management_System.Submission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByUserId(Long userId);

}