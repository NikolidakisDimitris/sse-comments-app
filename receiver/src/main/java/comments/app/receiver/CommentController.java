package comments.app.receiver;

import comments.app.components.comment.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.*;

@Slf4j
@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    private CommentPublisher commentPublisher;


    //todo: add response object, error response object, ExceptionControllerHandler
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/add")
    public void insertComment(@RequestBody CommentRequest commentRequest){


        log.info(commentRequest.getMessage());
        Comment comment = Comment.builder()
                .message(commentRequest.getMessage())
                .name(commentRequest.getName())
                .timestamp(System.currentTimeMillis() / 100L)
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))).build();
        this.commentPublisher.publishInsert(comment);
        log.info("insert message, {}", commentRequest);
    }
}
