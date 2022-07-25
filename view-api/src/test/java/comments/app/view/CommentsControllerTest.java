package comments.app.view;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import comments.app.commons.comment.*;
import comments.app.commons.mongodb.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.boot.test.json.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.*;
import org.springframework.mock.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(MockitoExtension.class)
class CommentsControllerTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentsController commentsController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        JacksonTester.initFields(this, JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build());

        mockMvc = MockMvcBuilders.standaloneSetup(commentsController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }


    @Test
    void success() throws Exception {

        Pageable paging = PageRequest.of(
                0, 10, Sort.by("timestamp").descending());

        given(commentRepository.findAll(paging)).willReturn(mockPageData(10));

        MockHttpServletResponse response = mockMvc.perform(get("/view-api/v1/comments/getAll"))
                .andReturn().getResponse();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Comment> commentList = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Comment>>(){});

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(10, commentList.size());
    }

    //x is the number of comments of this page
    private static Page<MongodbComment> mockPageData(int x) {

        return new PageImpl<MongodbComment>(mockMongodbComments(x));
    }

    //return x fake comments
    private static List<MongodbComment> mockMongodbComments(int x) {


        List<MongodbComment> mockData = new ArrayList<>();
        MongodbComment mockDbComment;
        for (int i = 0; i < x; i++) {
            mockDbComment = new MongodbComment();
            mockDbComment.setId(UUID.randomUUID().toString());
            mockDbComment.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            mockDbComment.setMessage("test message " + (i + 1));
            mockDbComment.setName("Guest");
            mockDbComment.setTimestamp(System.currentTimeMillis() + i);

            mockData.add(mockDbComment);
        }
        return mockData;
    }
}