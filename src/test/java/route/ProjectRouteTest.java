package route;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

import static org.junit.Assert.assertEquals;

public class ProjectRouteTest {


    @Rule
    public ExpectedException exception =
            ExpectedException.none();
    private ProjectRouteContext projectRouteContext = new ProjectRouteContext();
    private Client client = new Client(Protocol.HTTP);
    private Request request;
    private Response response;

    @Before
    public final void before() throws Exception {
        projectRouteContext = new ProjectRouteContext();
        projectRouteContext.run();

    }
    @After
    public void after() throws Exception {
        projectRouteContext.stop();
    }

    @Test
    public void TestGetAllProject() throws Exception {


        String url = "http://localhost:9091/project";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.GET, url);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
        Assert.assertTrue(response.isEntityAvailable());
        Assert.assertEquals(MediaType.TEXT_PLAIN, response.getEntity().getMediaType());
        String responseString = response.getEntityAsText();
        Assert.assertNotNull(responseString);
        projectRouteContext.stop();
    }

    @Test
    public void TestGetProjectById() throws Exception {

        String url = "http://localhost:9091/project/7";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.GET, url);
        response = client.handle(request);

        assertEquals(200, response.getStatus().getCode());
        Assert.assertTrue(response.isEntityAvailable());
        Assert.assertEquals(MediaType.TEXT_PLAIN, response.getEntity().getMediaType());
        String responseString = response.getEntityAsText();
        Assert.assertNotNull(responseString);
        projectRouteContext.stop();
    }

    @Test
    public void TestPostProject() throws Exception {

        String url = "http://localhost:9091/project";
        String post = "{\"projectName\": \"JUnitTest\"}";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.POST, url);

        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(201, response.getStatus().getCode());
        projectRouteContext.stop();
    }

    @Test
    public void TestPutProjectById() throws Exception {

        String url = "http://localhost:9091/project/11";
        String post = "{\"projectName\": \"puting test\"}";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.PUT, url);

        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
        projectRouteContext.stop();
    }

    @Test
    public void TestDeleteProjectById() throws Exception {
        String url = "http://localhost:9091/project/11";
        String post = "{\"projectName\": \"puting test\"}";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.DELETE, url);
        response = client.handle(request);
        assertEquals(204, response.getStatus().getCode());
        projectRouteContext.stop();
    }




}
