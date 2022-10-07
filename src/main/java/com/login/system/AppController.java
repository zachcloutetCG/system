package com.login.system;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

import org.hibernate.Hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;


import org.springframework.util.StringUtils;

import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.ui.*;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
//@RestController
@Controller
public class AppController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private AssignmentRepository assignmentRepo;


    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user)
    {
        if(userRepo.findByEmail(user.getEmail())==null)
        {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRoles(user.getRoles(),new Role(user.getRole()));
            userRepo.save(user);
            return "register_success";
        }
        else
        {
            return "signup_form";
        }
       
    }
    @GetMapping("/view_course")
    public String viewCourse(Model model,@RequestParam Long courseId){
        //System.out.println(courseId);
        Course course = courseRepo.getReferenceById(courseId);
        assignmentRepo.getReferenceById(courseId);
        //course.setAssignments();
        //System.out.println(course.getInstructor());
        model.addAttribute("course",course);
        return "view_course";
    }
    @PostMapping("/create_assignment")
    public String assignmentPage(Model model, Long id){
        //System.out.println(id);
        Course course = courseRepo.getReferenceById(id);
        model.addAttribute("course",course);
        Assignment assignment = new Assignment();
        assignment.setCourse(course);
        model.addAttribute("assignment",assignment) ;
        return "create_assignment";

    }
    @PostMapping("process_assignment")
    public String processAssignment(Model model,Assignment assignment, @RequestParam("file")MultipartFile file){
        System.out.println(assignment.getName());        
        System.out.println(assignment.getCourse().getName());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //assignment.setName(fileName);
        try{
            assignment.setContent(file.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
        assignment.setCourseId(assignment.getCourse().getId());
        assignmentRepo.save(assignment);
        Course course = assignment.getCourse();
        //courseRepo.save(course);


        //assignment.setContent(file);
        return "register_success";
    }
    @GetMapping("/course_creation_page")
    public String createCourse(Model model){
        model.addAttribute("course", new Course());
        return "create_course";
    }
    @PostMapping("/process_course")
    public String processCourse(Model model,Course course){
        if(courseRepo.findByName(course.getName()) == null){
            courseRepo.save(course);
            model.addAttribute("map",new UserToCourseMap());
            return "register_success";
        }
        else
        {
            return "create_course";
        }
       
    }
    
    @GetMapping("/list_user")
    public String listUsers(Model model){
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        //List<Course> courses = new ArrayList<>(.getCourses());
        model.addAttribute("map", new UserToCourseMap());
        return"list_user";
    }
    @GetMapping("/list_courses")
    public String listCourses(Model model,Long userId){
        //System.out.println(userId);

        return "list_courses";
    }
    @PostMapping("/edit_user")
    public String editUser(Model model, @RequestParam(name = "user")User user){
        //System.out.println(user);
        return "edit_user";

    }
    @GetMapping("/users")
    public String Users(Model model,Principal principal){
        final String loggedInUser = principal.getName();
        User currUser = userRepo.findByEmail(loggedInUser);
        model.addAttribute(currUser);
        List<Course> courses = new ArrayList<>(currUser.getCourses());
        //System.out.println(courses.get(0).getName());
        model.addAttribute("courseList",courses);

        model.addAttribute("map",new UserToCourseMap());
        return "users";
    }
    @GetMapping("/course_enroll")
    public String courseEnroll(Model model){
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        //model.addAttribute("user", new User());
        List<Course> listCourses = courseRepo.findAll();
        model.addAttribute("listCourses", listCourses);
        //model.addAttribute("course", new Course());
        model.addAttribute("map",new UserToCourseMap());
        return "course_enroll";
    }
    @PostMapping("/enroll")
    public String enroll(Model model,Long userId,Long courseId){
        User userToUpdate = userRepo.getReferenceById(userId);
        Course course = courseRepo.getReferenceById(courseId);
    
        if(userToUpdate.getCourses().contains(course)){
            List<User> listUsers = userRepo.findAll();
            model.addAttribute("listUsers", listUsers);
            List<Course> listCourses = courseRepo.findAll();
            model.addAttribute("listCourses", listCourses);
        
            model.addAttribute("map",new UserToCourseMap());
            return "course_enroll";        
        }
        else{
            //User userToUpdate = userRepo.getReferenceById(user.getId());
            //User userToUpdate = userRepo.findByEmail(user.getEmail());
            userToUpdate.setCourses(userToUpdate.getCourses(), course);
            userRepo.save(userToUpdate);
            //model.addAttribute("map",new UserToCourseMap());
            return "register_success";
        }
        

        
        
    }
   
}