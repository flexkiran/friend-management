
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import static org.hamcrest.CoreMatchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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

    public void registerEmail_Success(String email) throws Exception {
        String json = toJson(ImmutableMap.of("email", email));
        mockMvc.perform(post("/person/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test(groups = "/person/register")
    public void registerEmail_EmailRegisterRepeatedApiException() throws Exception {
        String json = toJson(ImmutableMap.of("email", "zxcv@example.com"));
        mockMvc.perform(post("/person/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
        mockMvc.perform(post("/person/register").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("903"));
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_Success() throws Exception {
        String email1 = "andy@example.com", email2 = "john@example.com";
        registerEmail_Success(email1);
        registerEmail_Success(email2);
        createFriendConnection_Success(email1, email2);
    }

    public void createFriendConnection_Success(String email1, String email2) throws Exception {
        List<String> friends = Arrays.asList(email1, email2);
        String json = toJson(ImmutableMap.of("friends", friends));
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_UnreadableRequestApiException() throws Exception {
        String json = "asdf";
        Locale locale = new Locale("in");
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, locale.getLanguage()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error", is("901")))
                .andExpect(jsonPath("$.lang", is("in")))
                .andExpect(jsonPath("$.message", is("Data tidak dapat diproses")));
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_InvalidRequestApiException_NotNull() throws Exception {
        String json = "{\"friends\":null}";
        Locale locale = new Locale("in");
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, locale.getLanguage()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error", is("902")))
                .andExpect(jsonPath("$.lang", is("in")))
                .andExpect(jsonPath("$.message", is("Data tidak valid")))
                .andExpect(jsonPath("$.validationErrors[?(@.field == 'friends' && @.message == 'Tidak boleh null')]").exists());
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_InvalidRequestApiException_Size() throws Exception {
        String json = toJson(ImmutableMap.of("friends", Arrays.asList("andy@example.com")));
        Locale locale = new Locale("en");
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, locale.getLanguage()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error", is("902")))
                .andExpect(jsonPath("$.lang", is("en")))
                .andExpect(jsonPath("$.message", is("Message not valid")))
                .andExpect(jsonPath("$.validationErrors[?(@.field == 'friends' && @.message == 'Size must be between 2 and 2')]").exists());
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_InvalidEmailRequestApiException() throws Exception {
        String json = toJson(ImmutableMap.of("friends", Arrays.asList("com")));
        Locale locale = new Locale("in");
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, locale.getLanguage()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error", is("902")))
                .andExpect(jsonPath("$.lang", is("in")))
                .andExpect(jsonPath("$.message", is("Data tidak valid")))
                .andExpect(jsonPath("$.validationErrors[?(@.field == 'friends[0]' && @.message == \"'com' bukan email yang valid\")]").exists());
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_NotUniqueRequestApiException() throws Exception {
        String json = toJson(ImmutableMap.of("friends", Arrays.asList("andy@example.com", "andy@example.com")));
        Locale locale = Locale.ENGLISH;
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, locale.getLanguage()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error", is("902")))
                .andExpect(jsonPath("$.lang", is("en")))
                .andExpect(jsonPath("$.message", is("Message not valid")))
                .andExpect(jsonPath("$.validationErrors[?(@.field == 'friends[1]' && @.message == \"'andy@example.com' is not unique\")]").exists());
    }

    @Test(groups = "/friend/connect")
    public void createFriendConnection_EmailNotRegisteredApiException() throws Exception {
        List<String> friends = Arrays.asList("qwerty@example.com", "ytrewq@example.com");
        String json = toJson(ImmutableMap.of("friends", friends));
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("904"));
    }

    @Test(groups = "/friend/connect", dependsOnMethods = "createFriendConnection_Success")
    public void createFriendConnection_FriendConnectionRequestRepeatedApiException() throws Exception {
        List<String> friends = Arrays.asList("andy@example.com", "john@example.com");
        String json = toJson(ImmutableMap.of("friends", friends));
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("905"));
    }

    @Test(groups = "/friend/list", dependsOnGroups = "/friend/connect")
    public void retrieveFriendList_andy() throws Exception {
        String json = "{\"email\":\"andy@example.com\"}";
        mockMvc.perform(post("/friend/list").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.count", is(1)))
                .andExpect(jsonPath("$.friends[0]", is("john@example.com")));
    }

    @Test(groups = "/friend/list", dependsOnGroups = "/friend/connect")
    public void retrieveFriendList_john() throws Exception {
        String json = "{\"email\":\"john@example.com\"}";
        mockMvc.perform(post("/friend/list").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.count", is(1)))
                .andExpect(jsonPath("$.friends[0]", is("andy@example.com")));
    }

    @Test(groups = "/friend/list")
    public void retrieveFriendList_EmailNotRegisteredApiException() throws Exception {
        String json = "{\"email\":\"asdf@fdsa.asdf\"}";
        Locale locale = new Locale("in");
        mockMvc.perform(post("/friend/list").content(json).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, locale.getLanguage()))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error", is("904")))
                .andExpect(jsonPath("$.message", is("Email tidak terdaftar")));
    }

    @Test(groups = "/friend/common", dependsOnGroups = {"/friend/list"})
    public void retrieveCommonFriends() throws Exception {
        // register common@example.com
        registerEmail_Success("common@example.com");
        // create friend connection 
        createFriendConnection_Success("andy@example.com", "common@example.com");
        createFriendConnection_Success("john@example.com", "common@example.com");
        // retieve common friends 
        String json = toJson(ImmutableMap.of("friends", Arrays.asList("andy@example.com", "john@example.com")));
        mockMvc.perform(post("/friend/common").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.friends[0]", is("common@example.com")));
    }

    @Test(groups = "/friend/subscribe")
    public void createSubscription_Success() throws Exception {
        registerEmail_Success("lisa@example.com");
        createSubscription_Success("lisa@example.com", "john@example.com");
    }

    public void createSubscription_Success(String requestor, String target) throws Exception {
        String json = toJson(ImmutableMap.of("requestor", requestor, "target", target));
        mockMvc.perform(post("/friend/subscribe").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test(groups = "/friend/subscribe", dependsOnMethods = "createSubscription_Success")
    public void createSubscription_SubscriptionRequestRepeatedApiException() throws Exception {
        String json = toJson(ImmutableMap.of("requestor", "lisa@example.com", "target", "john@example.com"));
        mockMvc.perform(post("/friend/subscribe").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("906"));
    }

    @Test(groups = "/friend/block", dependsOnGroups = {"/friend/connect"})
    public void createBlockingBetweenConnectedFriends_Success() throws Exception {
        createBlocking_Success("andy@example.com", "john@example.com");
    }

    public void createBlocking_Success(String requestor, String target) throws Exception {
        String json = toJson(ImmutableMap.of("requestor", requestor, "target", target));
        mockMvc.perform(post("/friend/block").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test(groups = "/friend/block", dependsOnMethods = "createBlockingBetweenConnectedFriends_Success")
    public void createBlockingBetweenConnectedFriends_BlockingRequestRepeatedApiException() throws Exception {
        String json = toJson(ImmutableMap.of("requestor", "andy@example.com", "target", "john@example.com"));
        mockMvc.perform(post("/friend/block").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("907"));
    }

    // if they are not connected as friends, then no new friends connection can be added
    @Test(groups = "/friend/block", dependsOnGroups = {"/friend/list"})
    public void createFriendConnection_BlockedFriendApiException() throws Exception {
        registerEmail_Success("blocked@example.com");
        createBlocking_Success("andy@example.com", "blocked@example.com");
        String json = toJson(ImmutableMap.of("friends", Arrays.asList("andy@example.com", "blocked@example.com")));
        Locale locale = new Locale("en");
        mockMvc.perform(post("/friend/connect").content(json).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.ACCEPT_LANGUAGE, locale.getLanguage()))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error", is("908")))
                .andExpect(jsonPath("$.message", is("Blocked friend")));
    }

    @Test(groups = "/updates", dependsOnGroups = {"/friend/block"})
    public void retrieveUpdatesReceiver_Success() throws Exception {
        // register kate
        registerEmail_Success("kate@example.com");

        // post updates
        String json = "{"
                + "\"sender\":\"john@example.com\""
                + ",\"text\":\"Hello World! kate@example.com\""
                + "}";
        mockMvc.perform(post("/updates").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.recipients[?(@ == 'lisa@example.com')]").exists())
                .andExpect(jsonPath("$.recipients[?(@ == 'kate@example.com')]").exists());
    }
}
