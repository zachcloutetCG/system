package com.login.system;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    @Autowired 
    private RoleRepository roleRepo;

    @Test
    public void testCreateUser(){
        User user = new User();
        Role role = new Role("Admin");
        Role role1 = new Role("User");
        Role role2 = new Role("Instructor");
        user.setEmail("admin@gmail.com");
        user.setPassword("adminpass");
        user.firstName("zach");
        user.lastName("Clou");
        //user.setRoles(role);

        
        //User user1 = new User("user@gmail.com", "userpassword", "student", "buddy", roles);
        //User user2 = new User("instructor@gmail.com", "instructpassword", "student", "buddy", roles);

        Role savedRole = roleRepo.save(role);
        Role savedRole1 = roleRepo.save(role1);
        roleRepo.save(role2);

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }
    
}
