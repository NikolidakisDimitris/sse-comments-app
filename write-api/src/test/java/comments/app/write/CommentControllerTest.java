package comments.app.write;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import comments.app.commons.comment.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.boot.test.json.*;
import org.springframework.http.*;
import org.springframework.mock.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;

import java.time.*;
import java.time.format.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentPublisher commentPublisher;

    @InjectMocks
    private CommentController commentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {

        JacksonTester.initFields(this, JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build());

        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void success() throws Exception {

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setMessage("a message");
        commentRequest.setName("Guest");

        String payload = objectMapper.writeValueAsString(commentRequest);

        MockHttpServletResponse response = mockMvc.perform(post("/write-api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andReturn().getResponse();

        Comment comment = Comment.builder()
                .message(commentRequest.getMessage())
                .name(commentRequest.getName())
                .timestamp(System.currentTimeMillis() / 100L)
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();

        Assertions.assertEquals(200, response.getStatus());
        Mockito.verify(commentPublisher).publishInsert(comment);
    }
}