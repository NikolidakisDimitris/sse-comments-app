package com.comments.examplemodule;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void insertComment(CommentRequest commentRequest){
        this.commentRepository.insert(new Comment(commentRequest));
    }

    public List<Comment> getAllComments(){

        return this.commentRepository.findAll();
    }



}
