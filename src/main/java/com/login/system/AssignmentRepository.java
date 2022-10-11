package com.login.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;


public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    @Query("SELECT u FROM Assignment u WHERE u.content = ?1")
    public User findByFile(String file);

}