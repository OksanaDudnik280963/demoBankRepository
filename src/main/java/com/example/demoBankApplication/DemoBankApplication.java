package com.example.demoBankApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.demoBankApplication.repository")
@EntityScan("com.example.demoBankApplication.entity")
@SpringBootApplication(scanBasePackages = {"com.example.demoBankApplication"})//, exclude={SecurityAutoConfiguration.class})
public class DemoBankApplication {
  public static void main(String[] args) {
        SpringApplication.run(DemoBankApplication.class, args);
    }

}
