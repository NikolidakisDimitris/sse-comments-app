package com.comments.examplemodule;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document
@Data
@NoArgsConstructor
public class Comment {

    public Comment(CommentRequest commentRequest){

        this.message = commentRequest.getMessage();
    }


    @Id
    private String id;
    private String message;

}
