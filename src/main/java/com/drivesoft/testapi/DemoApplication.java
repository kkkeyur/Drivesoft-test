package com.drivesoft.testapi;

import com.drivesoft.testapi.configs.ExampleProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({ExampleProperties.class})
public class DemoApplication extends SpringBootServletInitializer
		implements ApplicationListener<ApplicationReadyEvent> {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// Get the port from the environment or use a default value
		String port = event.getApplicationContext().getEnvironment().getProperty("server.port", "8080");

		// Print the Swagger UI URL
		log.info("Swagger UI: http://localhost:" + port + "/swagger-ui/");
	}
}
