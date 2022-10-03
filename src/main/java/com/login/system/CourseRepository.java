package com.login.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;


public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT u FROM Course u WHERE u.name = ?1")
    public Course findByName(String name);
     
}