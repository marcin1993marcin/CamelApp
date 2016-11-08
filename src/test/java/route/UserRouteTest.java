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
import static org.junit.Assert.assertTrue;

public class UserRouteTest {

    @Rule
    public ExpectedException exception =
            ExpectedException.none();
    private UserRouteContext userRouteContext = new UserRouteContext();
    private Client client = new Client(Protocol.HTTP);
    private Request request;
    private Response response;

    @Before
    public final void before() throws Exception {
        userRouteContext = new UserRouteContext();
        userRouteContext.run();
    }

    @After
    public void after() throws Exception {
        userRouteContext.stop();
    }

    @Test
    public void TestGetAllUser() throws Exception {


        String url = "http://localhost:9091/user";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.GET, url);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
        assertTrue(response.isEntityAvailable());
        Assert.assertEquals(MediaType.TEXT_PLAIN, response.getEntity().getMediaType());
        String responseString = response.getEntityAsText();
        Assert.assertNotNull(responseString);

    }

    @Test
    public void TestGetUserById() throws Exception {

        String url = "http://localhost:9091/user/12";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.GET, url);
        response = client.handle(request);

        if (response.isEntityAvailable()) {
            assertEquals(200, response.getStatus().getCode());
            Assert.assertEquals(MediaType.TEXT_PLAIN, response.getEntity().getMediaType());
        } else {
            assertEquals(204, response.getStatus().getCode());
        }


    }

    @Test
    public void TestPostUser() throws Exception {

        String url = "http://localhost:9091/user";
        String post = " {  \"id\": 9, \"firstName\": \"First\", \"lastName\": \"Name\",  \"email\": \"email\",   \"status\": \"pp\" }";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.POST, url);

        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(400, response.getStatus().getCode());

    }

    @Test
    public void TestPutUserById() throws Exception {

        String url = "http://localhost:9091/user/12";
        String post = " {  \"id\": 9, \"firstName\": \"First\", \"lastName\": \"Name\",  \"email\": \"email\",   \"status\": \"pp\" }";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.PUT, url);

        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertTrue(response.getStatus().getCode() == 200 || response.getStatus().getCode() == 400);

    }

    @Test
    public void TestDeleteUserById() throws Exception {
        String url = "http://localhost:9091/user/11";

        client = new Client(Protocol.HTTP);
        request = new Request(Method.DELETE, url);
        response = client.handle(request);
        assertTrue(response.getStatus().getCode() == 204 || response.getStatus().getCode() == 304);

    }

    @Test
    public void TestDeleteAll() throws Exception {
        String url = "http://localhost:9091/user";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.DELETE, url);
        response = client.handle(request);
        assertEquals(304, response.getStatus().getCode());
    }

}
