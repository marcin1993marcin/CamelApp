package route;

import com.app.camel.routes.PositionRoute;
import org.junit.*;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.restlet.data.Status.SUCCESS_CREATED;
import static org.restlet.data.Status.SUCCESS_NO_CONTENT;
import static org.restlet.data.Status.SUCCESS_OK;

public class PositionRouteTest extends RouteTest {

    private static final RouteContext routeContext = new RouteContext(new PositionRoute());
    private static final String REQUEST_JSON_LOCATION = "requests/positionResource/";

    public PositionRouteTest() {
        super("position");
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
    public void shouldReturnAllPositions() throws Exception {
        // given
        request = createRequest(Method.GET);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned positions list").isEqualTo(SUCCESS_OK);

    }

    @Test
    public void shouldReturnPositionById() throws Exception {
        // given
        String url = "1";
        request = createRequest(Method.GET, url);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned position by id %s", url).isEqualTo(SUCCESS_OK);
    }

    @Test
    public void shouldCreatePosition() throws Exception {
        // given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.POST);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Position created successfully").isEqualTo(SUCCESS_CREATED);

    }

    @Test
    public void shouldUpdatePositionById() throws Exception {
        // given
        String url = "1";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.PUT, url);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Position updated successfully").isEqualTo(SUCCESS_CREATED);

    }

    @Test
    public void shouldDeletePositionById() throws Exception {
        // given
        String url = "1";
        request = createRequest(Method.DELETE, url);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete position by id %s", url).isEqualTo(SUCCESS_NO_CONTENT);

    }
}
