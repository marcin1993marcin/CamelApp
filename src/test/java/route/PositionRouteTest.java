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

public class PositionRouteTest {


    @Rule
    public ExpectedException exception =
            ExpectedException.none();
    private PositionRouteContext positionRouteContext = new PositionRouteContext();
    private Client client = new Client(Protocol.HTTP);
    private Request request;
    private Response response;

    @Before
    public final void before() throws Exception {
        positionRouteContext = new PositionRouteContext();
        positionRouteContext.run();
    }

    @After
    public void after() throws Exception {
        positionRouteContext.stop();
    }

    @Test
    public void TestGetAllPosition() throws Exception {
        String url = "http://localhost:9091/position";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.GET, url);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
        Assert.assertTrue(response.isEntityAvailable());
        Assert.assertEquals(MediaType.TEXT_PLAIN, response.getEntity().getMediaType());
        String responseString = response.getEntityAsText();
        Assert.assertNotNull(responseString);
    }

    @Test
    public void TestGetPositionById() throws Exception {
        String url = "http://localhost:9091/position/12";
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
    public void TestPostPosition() throws Exception {
        String url = "http://localhost:9091/position";
        String post = " { \"id\": 9, \"position\": \"Tester\"}";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.POST, url);
        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(201, response.getStatus().getCode());
    }

    @Test
    public void TestPutPositionById() throws Exception {
        String url = "http://localhost:9091/position/5";
        String post = " { \"id\": 5, \"position\": \"UpdatedTester\"}";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.PUT, url);
        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
    }

//    @Test
//    public void TestDeletePositionById() throws Exception {
//        String url = "http://localhost:9091/position/2";
//        client = new Client(Protocol.HTTP);
//        request = new Request(Method.DELETE, url);
//        response = client.handle(request);
//        assertEquals(204, response.getStatus().getCode());
//    }

//    @Test
//    public void TestDeleteAll() throws Exception {
//        String url = "http://localhost:9091/position";
//        client = new Client(Protocol.HTTP);
//        request = new Request(Method.DELETE, url);
//        response = client.handle(request);
//        assertEquals(200, response.getStatus().getCode());
//    }

}
