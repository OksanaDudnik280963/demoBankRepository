package com.example.demoBankRepository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


@SpringBootTest
@ComponentScan(basePackages = {"com.example.demoBankRepository", "com.example.demoBankRepository.entity"})
class DemoBankApplicationTests {
    Connection c = null;
    Statement stmt = null;
    @Test
    void contextLoads() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_bank?reconnect=true&createDatabaseIfNotExist=true", "root", "Libra28091963!");
            System.out.println("Opened database successfully");

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

}
