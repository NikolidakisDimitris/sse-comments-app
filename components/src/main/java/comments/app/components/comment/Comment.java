package comments.app.components.comment;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class Comment {


    private String message;
    private Long timestamp;

    public Comment(String message, Long timestamp){
        this.message = message;
        this.timestamp = timestamp;
    }

    public Comment(String message){
        this.message = message;
    }

}
