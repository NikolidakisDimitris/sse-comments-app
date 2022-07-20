package comments.app.receiver;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(
        scanBasePackages = {
                "comments.app.components.rabbitmq"
        })
public class ReceiverApp {

    public static void main(String[] args) {

        SpringApplication.run(ReceiverApp.class, args);
    }
}
