package com.comments.examplemodule;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    public CommentService commentService;


    //todo: add response object, error response object, ExceptionControllerHandler
    @PostMapping
    public void insertComment(@RequestBody CommentRequest commentRequest){

        log.info(commentRequest.getMessage());
        this.commentService.insertComment(commentRequest);
        log.info("insert message, {}", commentRequest);
    }

    @GetMapping
    public List<Comment> getAllComments(){

        return  commentService.getAllComments();
    }
}
