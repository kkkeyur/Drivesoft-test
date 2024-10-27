package com.drivesoft.testapi.configs.security;

import com.drivesoft.testapi.entities.security.User;
import com.drivesoft.testapi.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitialiser {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            // Check if the user "admin" already exists
            if (userRepository.findByUsername("admin") == null) {
                // Encrypt the password before saving
                String encodedPassword = passwordEncoder.encode("DriveSoft@@!");

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encodedPassword);
                userRepository.save(admin);
                log.info("Admin user created.");
            } else {
                log.info("Admin user already exists.");
            }
        };
    }
}

