package com.login.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
     
}