package comments.app.receiver;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class CommentRequest {

    @NotEmpty
    private String message;

    //instantiate in case of unnamed user
    private String name = "Guest";
}
