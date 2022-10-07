package com.login.system;

import javax.persistence.Entity;

//import org.springframework.context.support.BeanDefinitionDsl.Role;
import java.sql.Blob;
import java.sql.Date;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="assignments")
public class Assignment {

    @Id
    @Column(name = "assign_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Course course;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "assign_name", nullable = false, length=50)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    @Lob
    private byte[] content;

    public Assignment(){

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


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }


    public Long getCourseId() {
        return this.courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
}