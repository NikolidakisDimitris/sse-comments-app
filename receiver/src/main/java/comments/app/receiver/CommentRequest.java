package comments.app.receiver;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class CommentRequest {

    @NotEmpty
    private String message;
    @NotEmpty
    private String name;
}
