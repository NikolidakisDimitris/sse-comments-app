package comments.app.write;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class CommentRequest {

    @NotBlank
    private String message;
    private String name;
}
