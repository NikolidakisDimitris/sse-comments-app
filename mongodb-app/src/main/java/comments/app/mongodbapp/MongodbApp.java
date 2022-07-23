package comments.app.mongodbapp;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.mongodb.repository.config.*;

@SpringBootApplication(
        scanBasePackages = {
                "comments.app.mongodbapp",
                "comments.app.commons"
        }
)
@EnableMongoRepositories(basePackages = {"comments.app.commons.mongodb"})
public class MongodbApp {

    public static void main(String[] args) {

        SpringApplication.run(MongodbApp.class, args);
    }
}
