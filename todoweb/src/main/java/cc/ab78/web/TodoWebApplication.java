package cc.ab78.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TodoWebApplication {

    public static void start() {
        SpringApplication.run(TodoWebApplication.class);
        // Impede que a thread principal termine
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(TodoWebApplication.class, args);
    }
}
