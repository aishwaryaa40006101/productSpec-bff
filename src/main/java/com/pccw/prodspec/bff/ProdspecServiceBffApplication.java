package com.pccw.prodspec.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * SprintBoot application class
 *
 * @author 20023424
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.pccw.*")
public class ProdspecServiceBffApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProdspecServiceBffApplication.class, args);
    }
}
