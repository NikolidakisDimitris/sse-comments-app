package comments.app.examplemodule;

import comments.app.rabbitmq.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

@SpringBootApplication(
        scanBasePackages = {
                "comments.app.examplemodule",
                "comments.app.rabbitmq"
        })
//@SpringBootApplication
public class ExampleModuleApp {


    public static void main(String[] args) {

        SpringApplication.run(ExampleModuleApp.class, args);
    }

    //we can use this to create mock data upon start-up, commenting in/out is not a nice way though :P
    //maybe use a property as a switch
//    @Bean
//    CommandLineRunner runner(CommentRepository commentRepository) {
//
//        return args -> {
//            Comment comment = new Comment();
//            comment.setMessage("this is a comment");
//            commentRepository.insert(comment);
//        };
//    }

//    @Bean
//    CommandLineRunner runner(
//            RabbitMQMessageProducer producer,
//            CommentConfig commentConfig
//    ) {
//
//        return args -> {
//            Comment comment = new Comment();
//            comment.setMessage("this is a comment");
//            producer.publish(
//                    comment,
//                    commentConfig.getInternalExchange(),
//                    commentConfig.getInternalNotificationRoutingKey());
//        };
//    }
}

//    @Bean
//    CommandLineRunner commandLineRunner(
//            RabbitMQMessageProducer producer,
//            NotificationConfig notificationConfig
//            ) {
//        return args -> {
//            producer.publish(
//                    new Person("Ali", 18),
//                    notificationConfig.getInternalExchange(),
//                    notificationConfig.getInternalNotificationRoutingKey());
//        };
//    }
