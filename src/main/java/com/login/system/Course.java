package com.login.system;

import javax.persistence.*;

//import org.springframework.context.support.BeanDefinitionDsl.Role;

import java.util.*;

@Entity
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "course_name", nullable = false, length=50)
    private String name;
    
    @Column(name = "department", nullable = false, length=30)
    private String department;

    @Column(name = "instructor", nullable = false, length=30)
    private String instructor;

    @OneToMany(mappedBy = "course",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Assignment> assignments = new ArrayList<>();

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

    public List<Assignment> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
    public void addAssignment(Assignment assignment){
        assignments.add(assignment);
        assignment.setCourse(this);
    }
    public void removeAssignment(Assignment assignment){
        assignments.remove(assignment);
        assignment.setCourse(null);
    }

}
