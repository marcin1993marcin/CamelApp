package route;

import org.junit.*;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import route.context.SkillRouteContext;
import util.ReadResources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.restlet.data.Status.*;

public class SkillRouteTest extends RouteTest {

    private static final SkillRouteContext skillRouteContext = new SkillRouteContext();
    private static final Client client = new Client(Protocol.HTTP);
    private static final String REQUEST_JSON_LOCATION = "requests/skillResource/";

    private Request request;
    private Response response;
    private ReadResources readResources = new ReadResources();

    public SkillRouteTest() {
        super("skill");
    }

    @BeforeClass
    public static void before() throws Exception {
        skillRouteContext.run();
    }

    @AfterClass
    public static void after() throws Exception {
        skillRouteContext.stop();
    }

    @Test
    public void shouldReturnAllSkills() throws Exception {
        // given
        request = createRequest(Method.GET);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned skills list").isEqualTo(SUCCESS_OK);
    }

    @Test
    public void shouldReturnSkillById() throws Exception {
        // given
        String id = "1";
        request = createRequest(Method.GET, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Return user by id %s", id).isEqualTo(SUCCESS_OK);
    }

    @Test
    public void shouldNotReturnSkillById() throws Exception {
        // given
        String id = "7357";
        request = createRequest(Method.GET, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Skill with id %s not exist", id).isEqualTo(SUCCESS_NO_CONTENT);
    }

    @Test
    public void shouldCreateSkill() throws Exception {
        // given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.POST);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Skill created").isEqualTo(SUCCESS_CREATED);
    }

    @Test
    public void shouldNotCreateSkill() throws Exception {
        // given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "invalidPostRequestBody.json");
        request = createRequest(Method.POST);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Server error").isEqualTo(SERVER_ERROR_SERVICE_UNAVAILABLE);
    }

    @Test
    public void shouldUpdateSkill() throws Exception {
        // given
        String id = "1";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctPutRequestBody.json");
        request = createRequest(Method.PUT, id);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Skill update").isEqualTo(SUCCESS_ACCEPTED);
    }

    @Test
    public void shouldNotUpdateSkill() throws Exception {
        // given
        String id = "1";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "invalidPutRequestBody.json");
        request = createRequest(Method.PUT, id);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned server response").isEqualTo(REDIRECTION_NOT_MODIFIED);
    }

    @Test
    public void shouldDeleteSkillById() throws Exception {
        // given
        String id = "1";
        request = createRequest(Method.DELETE, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete user by id %s", id).isEqualTo(SUCCESS_NO_CONTENT);
    }

    @Test
    public void shouldDeleteAllSkills() {
        // given
        request = createRequest(Method.DELETE);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete all skills").isEqualTo(SUCCESS_NO_CONTENT);
    }
}
