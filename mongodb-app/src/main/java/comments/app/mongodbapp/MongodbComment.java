package comments.app.mongodbapp;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;
import comments.app.components.comment.Comment;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
@NoArgsConstructor
public class MongodbComment extends Comment {

    @Id
    private String id;

    public MongodbComment(Comment comment){
        super(comment.getMessage(), comment.getTimestamp());
    }
}
