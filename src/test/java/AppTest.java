
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import static org.hamcrest.CoreMatchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rais.friendmanagement.Application;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
public class AppTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    private String toJson(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection() throws Exception {
        String json = toJson(ImmutableMap.of("friends", Arrays.asList("andy@example.com", "john@example.com")));
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_UnreadableRequestApiException() throws Exception {
        String json = "asdf";
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_InvalidRequestApiException_NotNull() throws Exception {
        String json = "{}";
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_InvalidRequestApiException_Size() throws Exception {
        String json = toJson(ImmutableMap.of("friends", Arrays.asList("andy@example.com")));
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)));
    }
}
