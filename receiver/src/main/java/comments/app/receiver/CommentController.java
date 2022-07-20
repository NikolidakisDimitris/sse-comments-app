package comments.app.receiver;

import comments.app.components.comment.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    private CommentPublisher commentPublisher;


    //todo: add response object, error response object, ExceptionControllerHandler
    @PostMapping
    public void insertComment(@RequestBody CommentRequest commentRequest){


        log.info(commentRequest.getMessage());
        Comment comment = Comment.builder()
                .message(commentRequest.getMessage())
                .timestamp(System.currentTimeMillis() / 100L).build();
        this.commentPublisher.publishInsert(comment);
        log.info("insert message, {}", commentRequest);
    }
}
