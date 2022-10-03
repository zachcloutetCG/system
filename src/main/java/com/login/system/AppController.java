package com.login.system;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
import templates.UserToCourseMap;

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
            /*throw new
            result.rejectValue("email", null,
                    "There is already an account registered with the same email"); */
            return "signup_form";
        }
       
    }
    @GetMapping("/course_creation_page")
    public String createCourse(Model model){
        model.addAttribute("course", new Course());
        return "create_course";
    }
    @PostMapping("/process_course")
    public String processCourse(Course course){
        if(courseRepo.findByName(course.getName()) == null){
            courseRepo.save(course);
            return "users";
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
        return"list_user";
    }
    @PostMapping("/edit_user")
    public String editUser(Model model, @RequestParam(name = "user")User user){
        System.out.println(user);
        return "edit_user";

    }
    @GetMapping("/users")
    public String Users(Model model){

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
    public String enroll(Long userId,Long courseId){
        User userToUpdate = userRepo.getReferenceById(userId);
        Course course = courseRepo.getReferenceById(courseId);
    
        if(userToUpdate.getCourses().contains(course)){
            return "course_enroll";        
        }
        else{
            //User userToUpdate = userRepo.getReferenceById(user.getId());
            //User userToUpdate = userRepo.findByEmail(user.getEmail());
            userToUpdate.setCourses(userToUpdate.getCourses(), course);
            userRepo.save(userToUpdate);
            return "users";
        }
        

        
        
    }
   
}