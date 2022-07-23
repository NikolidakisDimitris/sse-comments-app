package comments.app.view;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(
        scanBasePackages = {
                "comments.app.view",
                "comments.app.commons.rabbitmq"
        })
public class SseApp {

    public static void main(String[] args) {

        SpringApplication.run(SseApp.class, args);
    }

}
