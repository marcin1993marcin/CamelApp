package route;

import com.app.camel.routes.UserRoute;
import org.junit.*;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.restlet.data.Status.*;

public class UserRouteTest extends RouteTest {

    private static final RouteContext routeContext = new RouteContext(new UserRoute());
    private static final String REQUEST_JSON_LOCATION = "requests/userResource/";


    public UserRouteTest() {
        super("user");
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        routeContext.run();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        routeContext.stop();
    }

    @Before
    public void before() {
        migrate.migrateDatabase();
    }

    @Test
    public void shouldReturnUsers() throws Exception {
        // given
        request = createRequest(Method.GET);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned users list").isEqualTo(SUCCESS_OK);

    }

    @Test
    public void shouldReturnUserById() throws Exception {
        // given
        String id = "2";
        request = createRequest(Method.GET, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Return user by id %s", id).isEqualTo(SUCCESS_OK);
    }

    @Test
    public void shouldNotReturnUserById() throws Exception {
        // given
        String id = "13";
        request = createRequest(Method.GET, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("User with id  %s not exist", id).isEqualTo(SUCCESS_NO_CONTENT);
    }

    @Test
    public void shouldNotReturnUserByInvalidId() throws Exception {
        // given
        String id = "noijoi";
        request = createRequest(Method.GET, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Incorrect ID", id).isEqualTo(CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        // given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.POST);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("User created").isEqualTo(SUCCESS_CREATED);
    }

    @Test
    public void shouldNotCreateUserWithEmptyBody() throws Exception {
        // given
        String post = "";
        request = createRequest(Method.POST);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("User with empty body").isEqualTo(CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void shouldNotCreateUserWithInvalidStatus() throws Exception {
        // given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "invalidStatusRequestBody.json");
        request = createRequest(Method.POST);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("User with bad Status").isEqualTo(CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void shouldNotUpdateUserWithInvalidStatus() throws Exception {
        // given
        String id = "12";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "invalidStatusRequestBody.json");
        request = createRequest(Method.PUT, id);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("returned server response").isEqualTo(CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void shouldUpdateUserById() throws  Exception {
        // given
        String id = "2";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.PUT, id);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("returned server response").isEqualTo(SUCCESS_CREATED);
    }

    @Test
    public void shouldDeleteUserById() throws Exception {
        // given
        String id = "2";
        request = createRequest(Method.DELETE, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete user by id %s", id).isEqualTo(SUCCESS_NO_CONTENT);

    }

    @Test
    public void shouldDeleteUsers() throws Exception {
        // given
        request = createRequest(Method.DELETE);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete all users").isEqualTo(SUCCESS_NO_CONTENT);
    }
}
