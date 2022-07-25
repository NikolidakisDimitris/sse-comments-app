package comments.app.view;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.mongodb.repository.config.*;

@SpringBootApplication(
        scanBasePackages = {
                "comments.app.view",
                "comments.app.commons"
        })
@EnableMongoRepositories(basePackages = {"comments.app.commons.mongodb"})
public class SseApp {

    public static void main(String[] args) {

        SpringApplication.run(SseApp.class, args);
    }
}