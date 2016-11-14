package route;

import databaseoperation.Migrate;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import route.context.UserSkillRouteContext;
import util.ReadResources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.restlet.data.Status.*;

public class UserSkillRouteTest extends RouteTest {

    private static final UserSkillRouteContext userSkillRouteContext = new UserSkillRouteContext();
    private static final Client client = new Client(Protocol.HTTP);
    private static final String REQUEST_JSON_LOCATION = "requests/userSkillResource/";

    private Request request;
    private Response response;
    private ReadResources readResources = new ReadResources();
    private Migrate migrate = new Migrate();


    public UserSkillRouteTest() {
        super("user", "skill");
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        userSkillRouteContext.run();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        userSkillRouteContext.stop();
    }

    @Before
    public void before() {
        migrate.migrateDatabase();
    }

    @Test
    public void shouldReturnAllUserSkillsByUserId() throws Exception {
        // given
        String id = "2";
        request = createRequest(Method.GET, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned user skills list").isEqualTo(SUCCESS_OK);
    }

    @Test
    public void shouldCreateAllUserSkills() throws Exception {
        // given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctPostRequestBody.json");
        request = createRequest(Method.POST, "2");
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("UserSkills created").isEqualTo(SUCCESS_CREATED);
    }

    @Test
    public void shouldUpdateAllUserSkills() throws Exception {
        // given
        String id = "2";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctPutRequestBody.json");
        request = createRequest(Method.PUT, id);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Updated user skills for user id %s", id).isEqualTo(SUCCESS_ACCEPTED);
    }

    @Test
    public void shouldNotUpdateAllUserSkills() throws Exception {
        // given
        String id = "2";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "invalidPutRequestBody.json");
        request = createRequest(Method.PUT, id);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned server response", id).isEqualTo(REDIRECTION_NOT_MODIFIED);
    }

    @Test
    public void shouldDeleteUserSkillsByUserId() throws Exception {
        // given
        String id = "2";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctDeleteRequestBody.json");
        System.out.println(post);
        request = createRequest(Method.DELETE, id);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete user skills by user id %s", id).isEqualTo(SUCCESS_NO_CONTENT);
    }

    @Test
    public void shouldDeleteAllUserSkillsByUserId() throws Exception {
        // given
        String id = "2";
        request = createRequest(Method.DELETE, id, "all");

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete all user skills by user id %s", id).isEqualTo(SUCCESS_NO_CONTENT);
    }
}
