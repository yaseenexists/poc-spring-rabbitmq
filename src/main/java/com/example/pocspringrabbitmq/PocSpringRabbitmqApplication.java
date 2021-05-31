package com.example.pocspringrabbitmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PocSpringRabbitmqApplication {

    @Bean
    public CommandLineRunner runner() {
        return new RabbitMqRunner();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PocSpringRabbitmqApplication.class, args);
    }
}
