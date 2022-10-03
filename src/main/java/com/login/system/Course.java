package com.login.system;

import javax.persistence.Entity;

//import org.springframework.context.support.BeanDefinitionDsl.Role;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="courses")
public class Course {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", nullable = false, length=20)
    private String name;
    
    @Column(name = "department", nullable = false, length=20)
    private String department;

    @Column(name = "instructor", nullable = false, length=20)
    private String instructor;

   

    public Course(){

    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructor() {
        return this.instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
