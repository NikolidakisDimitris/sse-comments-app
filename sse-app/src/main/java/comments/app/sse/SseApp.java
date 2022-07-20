package comments.app.sse;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(
        scanBasePackages = {
                "comments.app.sse",
                "comments.app.components.rabbitmq"
        })
//@SpringBootApplication
public class SseApp {

    public static void main(String[] args) {

        SpringApplication.run(SseApp.class, args);
    }

}
