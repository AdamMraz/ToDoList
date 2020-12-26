package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("main.model")
public class Source {
    public static void main(String[] args) {

        SpringApplication.run(Source.class, args);
    }
}
