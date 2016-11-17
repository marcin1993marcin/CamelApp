package route;

import com.app.camel.routes.SalaryRoute;
import org.junit.*;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.restlet.data.Status.SUCCESS_CREATED;
import static org.restlet.data.Status.SUCCESS_NO_CONTENT;
import static org.restlet.data.Status.SUCCESS_OK;

public class SalaryRouteTest extends RouteTest {

    private static final RouteContext routeContext = new RouteContext(new SalaryRoute());
    private static final String REQUEST_JSON_LOCATION = "requests/salaryResource/";

    public SalaryRouteTest() {
        super("salary");
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
    public void shouldReturnAllSalaries() throws Exception {
        // given
        request = createRequest(Method.GET);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned salaries list").isEqualTo(SUCCESS_OK);
    }

    @Test
    public void shouldReturnSalaryById() throws Exception {
        // given
        String id = "1";
        request = createRequest(Method.GET, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Return salary by id %s", id).isEqualTo(SUCCESS_OK);

    }

    @Test
    public void shouldCreateSalary() throws Exception {
        // given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.POST);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Salary created").isEqualTo(SUCCESS_CREATED);

    }

    @Test
    public void shouldUpdateSalaryById() throws Exception {
        // given
        String id = "1";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.PUT, id);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned server response").isEqualTo(SUCCESS_CREATED);

    }

    @Test
    public void shouldDeleteSalaryById() throws Exception {
        // given
        String id = "1";
        request = createRequest(Method.DELETE, id);

        // when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Delete salary by id %s", id).isEqualTo(SUCCESS_NO_CONTENT);

    }
}
