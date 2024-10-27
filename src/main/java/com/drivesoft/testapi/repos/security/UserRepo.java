package com.drivesoft.testapi.repos.security;

import com.drivesoft.testapi.entities.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

