package com.login.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import java.util.*;


public interface SubmissionRepository extends JpaRepository<DBFile, Long> {
    /*@Query("SELECT * FROM DBFile u WHERE u.assignment_id = ?1")
    public List<DBFile> findByID(Long id);
    */
     
}