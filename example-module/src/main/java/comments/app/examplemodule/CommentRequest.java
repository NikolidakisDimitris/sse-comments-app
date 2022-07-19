package comments.app.examplemodule;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class CommentRequest {

    @NotEmpty
    private String message;
}
