package comments.app.write;

import comments.app.commons.comment.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.time.*;
import java.time.format.*;

@Slf4j
@RestController
@RequestMapping("write-api/v1/comments")
public class CommentController {

    @Autowired
    private CommentPublisher commentPublisher;


    //todo: add response object, error response object, ExceptionControllerHandler
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public void insertComment(@Valid @RequestBody CommentRequest commentRequest) {

        String name = commentRequest.getName().isBlank() ? "Guest" : commentRequest.getName();
        log.info(commentRequest.getMessage());
        Comment comment = Comment.builder()
                .message(commentRequest.getMessage())
                .name(name)
                .timestamp(System.currentTimeMillis() / 100L)
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        this.commentPublisher.publishInsert(comment);
        log.info("insert message, {}", commentRequest);
    }
}
