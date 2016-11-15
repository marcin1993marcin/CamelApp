package route;

import databaseoperation.Migrate;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import util.ReadResources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.restlet.data.Status.SUCCESS_CREATED;
import static org.restlet.data.Status.SUCCESS_NO_CONTENT;
import static org.restlet.data.Status.SUCCESS_OK;
import static restconfiguration.RestConfiguration.REQUEST_URL;

public class SalaryRouteTest {

    private static final String REQUEST_CONTEXT = "salary";
    private static final String REQUEST_JSON_LOCATION = "requests/salaryResource/";

    @Rule
    public ExpectedException exception =
            ExpectedException.none();
    private SalaryRouteContext salaryRouteContext = new SalaryRouteContext();
    private Client client = new Client(Protocol.HTTP);
    private Request request;
    private Response response;
    private ReadResources readResources = new ReadResources();
    private Migrate migrate = new Migrate();

    @Before
    public final void before() throws Exception {
        salaryRouteContext = new SalaryRouteContext();
        salaryRouteContext.run();
        client = new Client(Protocol.HTTP);
        migrate.migrateDatabase();
    }

    @After
    public void after() throws Exception {
        salaryRouteContext.stop();
    }

    @Test
    public void shouldReturnAllSalaries() throws Exception {

        //given
        request = createRequest(Method.GET, "");

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Returned salaries list").isEqualTo(SUCCESS_OK);
    }

    @Test
    public void shouldReturnSalaryById() throws Exception {

        //given
        String url = "/1";
        request = createRequest(Method.GET, url);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Return salary by id %s", url).isEqualTo(SUCCESS_OK);

    }

    @Test
    public void shouldCreateSalary() throws Exception {

        //given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.POST, "");
        request.setEntity(post, MediaType.APPLICATION_ALL);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Salary created").isEqualTo(SUCCESS_CREATED);

    }

    @Test
    public void shouldUpdateSalaryById() throws Exception {

        // given
        String url = "/1";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.PUT, url);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        //when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("Returned server response").isEqualTo(SUCCESS_CREATED);

    }

    @Test
    public void shouldDeleteSalaryById() throws Exception {

        //given
        String url = "/1";
        request = createRequest(Method.DELETE, url);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Delete salary by id %s", url).isEqualTo(SUCCESS_NO_CONTENT);

    }

    private Request createRequest(Method method, String url) {
        return new Request(method, REQUEST_URL + REQUEST_CONTEXT + url);
    }
}
