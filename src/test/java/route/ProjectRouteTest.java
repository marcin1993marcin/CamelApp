package route;

import com.app.camel.routes.ProjectRoute;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.restlet.data.Status.*;

public class ProjectRouteTest extends RouteTest {

    private static final RouteContext routeContext = new RouteContext(new ProjectRoute());
    private static final String REQUEST_JSON_LOCATION = "requests/projectResource/";

    @Rule
    public ExpectedException exception =
            ExpectedException.none();

    public ProjectRouteTest() {
        super("project");
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
    public void shouldReturnProjects() throws Exception {
        // given
        request = createRequest(Method.GET);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned projects list").isEqualTo(SUCCESS_OK);

    }

    @Test
    public void shouldReturnProjectById() throws Exception {
        // given
        String url = "3";
        request = createRequest(Method.GET, url);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Return project by id %s", url).isEqualTo(SUCCESS_OK);

    }

    @Test
    public void shouldNotReturnProjectById() throws Exception {
        // given
        String url = "15";
        request = createRequest(Method.GET, url);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Return project by id  %s", url).isEqualTo(SUCCESS_NO_CONTENT);

    }

    @Test
    public void shouldNotReturnProjectByIncorrectId() {
        // given
        String url = "text";
        request = createRequest(Method.GET, url);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Request with incorrect ID  %s", url).isEqualTo(CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void shouldCreateProject() throws Exception {
        // given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.POST);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Project created ").isEqualTo(SUCCESS_CREATED);
    }

    @Test
    public void shouldUpdateProject() throws Exception {
        // given
        String url = "3";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.PUT, url);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("returned server response").isEqualTo(SUCCESS_CREATED);
    }

    @Test
    public void shouldDeleteProjectById() throws Exception {
        // given
        String url = "3";
        request = createRequest(Method.DELETE, url);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete project by id %s", url).isEqualTo(SUCCESS_NO_CONTENT);

    }

    @Test
    public void shouldDeleteAllProject() throws Exception {
        // given
        request = createRequest(Method.DELETE);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete all users").isEqualTo(SUCCESS_NO_CONTENT);
    }
}
