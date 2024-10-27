package com.drivesoft.testapi.controllers;


import com.drivesoft.testapi.models.AuthRequest;
import com.drivesoft.testapi.models.general.ApiResponse;
import com.drivesoft.testapi.repos.security.UserRepo;
import com.drivesoft.testapi.utils.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;

    private final UserRepo userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    /**
     * generateToken - to generate JWT token whitch will stay valid for 60 mins
     * @param authRequest
     * @returnResponseEntity<ApiResponse>
     */
    @PostMapping("/generateToken")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest authRequest) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();
        try {
            // Attempt to authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            // If successful, generate the token
            String token = jwtUtil.generateToken(authRequest.getUsername());
            log.info("Token retrieved: {}", token);


            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Token Generated Successfully");
            data.put("token", token);
            response.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", authRequest.getUsername(), e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Token Generation failed, check credentials");
            response.setData(data);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


}
