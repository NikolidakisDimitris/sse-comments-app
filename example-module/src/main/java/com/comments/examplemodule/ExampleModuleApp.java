package com.comments.examplemodule;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

@SpringBootApplication
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
}
