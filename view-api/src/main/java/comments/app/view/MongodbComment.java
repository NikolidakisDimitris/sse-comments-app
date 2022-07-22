package comments.app.view;

import comments.app.components.comment.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
@NoArgsConstructor
public class MongodbComment extends Comment {

    @Id
    private String id;

    //not very nice
    public MongodbComment(Comment comment){
        super(comment.getMessage(), comment.getTimestamp(), comment.getDate(), comment.getName());
    }
}

