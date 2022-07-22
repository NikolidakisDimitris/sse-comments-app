package comments.app.components.comment;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {


    private String message;
    private Long timestamp;
    private String date;
    private String name;

    public Comment(String message){
        this.message = message;
    }

}
