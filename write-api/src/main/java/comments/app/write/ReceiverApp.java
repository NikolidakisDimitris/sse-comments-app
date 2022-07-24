package comments.app.write;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(
        scanBasePackages = {
                "comments.app.receiver",
                "comments.app.commons"
        })
public class ReceiverApp {

    public static void main(String[] args) {

        SpringApplication.run(ReceiverApp.class, args);
    }
}
