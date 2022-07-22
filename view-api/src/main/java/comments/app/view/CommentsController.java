package comments.app.view;

import comments.app.components.comment.*;
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

        Pageable paging = PageRequest.of(
                0, 5, Sort.by("timestamp").descending());

        log.info("page: " + pageable.getPageNumber());
        log.info("size: " + pageable.getPageSize());

        Page<MongodbComment> page = commentRepository.findAll(pageable);

        /*
            private String message;
    private Long timestamp;
    private String date;
    private String name;
         */
//        return page.map(mc -> (Comment) mc);
        return page.getContent().stream().map(x -> (Comment) x).collect(Collectors.toList());
    }
}
