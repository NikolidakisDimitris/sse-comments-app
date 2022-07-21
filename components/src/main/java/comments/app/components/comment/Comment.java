package comments.app.components.comment;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class Comment {


    private String message;
    private Long timestamp;
    private String name;

    public Comment(String message, Long timestamp, String name){
        this.message = message;
        this.timestamp = timestamp;
        this.name = name;
    }

    public Comment(String message){
        this.message = message;
    }

}
