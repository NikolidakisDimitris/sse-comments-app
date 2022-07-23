package comments.app.mongodbapp;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(
        scanBasePackages = {
                "comments.app.mongodbapp",
                "comments.app.commons"
        }
)
public class MongodbApp {

    public static void main(String[] args) {

        SpringApplication.run(MongodbApp.class, args);
    }
}
