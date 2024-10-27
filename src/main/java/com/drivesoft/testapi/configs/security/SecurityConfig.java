package com.drivesoft.testapi.configs.security;

import com.drivesoft.testapi.services.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private  JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF as we are using JWT
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // Allow access to auth endpoints
                .antMatchers("/accDetails/fetchAndStoreData/**").permitAll()
                .antMatchers("/**/v2/api-docs/**", "/**/configuration/ui/**", "/**/swagger-resources/**",
                        "/**/configuration/security/**", "/swagger-ui.html", "/**/webjars/**",
                        "/**/swagger-ui/**", "/**/jacoco/**", "/getDomainInfo", "/getWorkAreaList", "/authenticate")
                .permitAll()
                .anyRequest().authenticated(); // Secure other endpoints

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests();
    }


}
