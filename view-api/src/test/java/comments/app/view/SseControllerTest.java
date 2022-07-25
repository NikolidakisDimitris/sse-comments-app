package comments.app.view;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.boot.test.json.*;
import org.springframework.mock.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class SseControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private SseController sseController;

    @BeforeEach
    void setUp() {

        JacksonTester.initFields(this, JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build());

        mockMvc = MockMvcBuilders.standaloneSetup(sseController).build();
    }

    @Test
    void testSse() throws Exception {


        MockHttpServletResponse response = mockMvc.perform(get("/view-api/v1/comments/sse"))
                .andExpect(status().isOk()).andReturn().getResponse();
        //Exiting but response remains open for further handling
        //we can test the listener and Sse emitter after making the connection

    }
}