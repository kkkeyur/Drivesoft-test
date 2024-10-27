//package com.drivesoft.testapi.configs.security;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//@Slf4j
//public class PasswordVerification {
//    public static void main(String[] args) {
//        String hashedPassword = "$10$eImiTXuWVxfY8W2I9N8EGeD.9dIHcZ9D2.0sXyK35I7qZB5pT8XbK";
//        String plainPassword = "DriveSoft@@!"; // Replace with the password to verify
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        boolean isMatch = encoder.matches(plainPassword, hashedPassword);
//
//        if (isMatch) {
//            log.info("The password matches!");
//        } else {
//            log.info("The password does not match.");
//        }
//    }
//}
//
