package comments.app.view;

import comments.app.commons.comment.*;
import comments.app.commons.mongodb.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;

@Slf4j
@RestController
public class CommentsController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping(value = "view-api/v1/comments/getAll")
    public List<Comment> getAllComments(@PageableDefault(page = 0, size = 10, sort = {"timestamp"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MongodbComment> page = commentRepository.findAll(pageable);

        return page.getContent().stream().map(x -> (Comment) x).collect(Collectors.toList());
    }
}
