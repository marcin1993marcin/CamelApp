package route;

import databaseoperation.Migrate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import util.ReadResources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.restlet.data.Status.*;
import static restconfiguration.RestConfiguration.REQUEST_URL;

public class ProjectRouteTest {

    private static final String REQUEST_CONTEXT = "project";
    private static final String REQUEST_JSON_LOCATION = "requests/projectResource/";

    @Rule
    public ExpectedException exception =
            ExpectedException.none();
    private ProjectRouteContext projectRouteContext = new ProjectRouteContext();
    private Client client = new Client(Protocol.HTTP);
    private Request request;
    private Response response;
    private ReadResources readResources = new ReadResources();
    private Migrate migrate= new Migrate();

    @Before
    public final void before() throws Exception {
        projectRouteContext = new ProjectRouteContext();
        projectRouteContext.run();
        client = new Client(Protocol.HTTP);
        migrate.migrateDatabase();
    }

    @After
    public void after() throws Exception {
        projectRouteContext.stop();
    }

    @Test
    public void shouldReturnProjects() throws Exception {

        //given
        request = createRequest(Method.GET, "");

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Returned projects list").isEqualTo(SUCCESS_OK);

    }

    @Test
    public void shouldReturnProjectById() throws Exception {


        //given
        String url = "/3";
        request = createRequest(Method.GET, url);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Return project by id %s", url).isEqualTo(SUCCESS_OK);

    }

    @Test
    public void shouldNotReturnProjectById() throws Exception {


        //given
        String url = "/15";
        request = createRequest(Method.GET, url);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Return project by id  %s", url).isEqualTo(SUCCESS_NO_CONTENT);

    }

    @Test
    public void shouldCreateProjectInDatabase() throws Exception {

        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.POST, "");
        request.setEntity(post, MediaType.APPLICATION_ALL);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Project created ").isEqualTo(SUCCESS_CREATED);
    }

    @Test
    public void shouldUpdateProject() throws Exception {

        // given
        String url = "/3";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.PUT, url);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        //when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("returned server response").isEqualTo(SUCCESS_CREATED);
    }

    @Test
    public void shouldDeleteProjectById() throws Exception {

        //given
        String url = "/3";
        request = createRequest(Method.DELETE, url);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Delete project by id %s", url).isEqualTo(SUCCESS_NO_CONTENT);

    }

    @Test
    public void TestDeleteAllProject() throws Exception {
        //given
        request = createRequest(Method.DELETE, "");

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Delete all users").isEqualTo(SUCCESS_NO_CONTENT);
    }

    private Request createRequest(Method method, String url) {
        return new Request(method, REQUEST_URL + REQUEST_CONTEXT + url);
    }

}
