package com.example.demoBankRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@EnableJpaRepositories("com.example.demoBankRepository.repository")
@EntityScan("com.example.demoBankRepository.entity")
@SpringBootApplication
public class DemoBankRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoBankRepositoryApplication.class, args);
    }
}
