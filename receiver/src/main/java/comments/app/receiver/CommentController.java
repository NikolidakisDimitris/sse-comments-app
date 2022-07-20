package comments.app.receiver;

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
        this.commentPublisher.publishInsert(commentRequest);
        log.info("insert message, {}", commentRequest);
    }
}
