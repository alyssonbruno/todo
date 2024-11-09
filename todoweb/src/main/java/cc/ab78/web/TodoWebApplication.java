package cc.ab78.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TodoWebApplication {

    public static void start() {
        SpringApplication.run(TodoWebApplication.class);
    }
}
