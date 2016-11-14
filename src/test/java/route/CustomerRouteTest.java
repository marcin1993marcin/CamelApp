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

public class CustomerRouteTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private CustomerRouteContext customerRouteContext = new CustomerRouteContext();
    private Client client = new Client(Protocol.HTTP);
    private Request request;
    private Response response;

    @Before
    public final void before() throws Exception{
        customerRouteContext = new CustomerRouteContext();
        customerRouteContext.run();
    }

    @After
    public void after() throws Exception{
        customerRouteContext.stop();
    }

    @Test
    public void TestGetAllCustomer() throws Exception{
        String url = "http://localhost:9091/customer";
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
    public void TestGetCustomerById() throws Exception {

        String url = "http://localhost:9091/customer/12";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.GET, url);
        response = client.handle(request);

        if (response.isEntityAvailable()) {
            assertEquals(200, response.getStatus().getCode());
            Assert.assertEquals(MediaType.TEXT_PLAIN, response.getEntity().getMediaType());
            String responseString = response.getEntityAsText();
        } else {
            assertEquals(204, response.getStatus().getCode());
        }


    }

    @Test
    public void TestPostCustomer() throws Exception {

        String url = "http://localhost:9091/customer";
        String post = " {  \"id\": 4, \"firstName\": \"Adam\", \"lastName\": \"Adam\",  \"email\": \"email\",   \"status\": \"pp\" }";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.POST, url);

        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(201, response.getStatus().getCode());

    }

    @Test
    public void TestPutCustomerById() throws Exception {

        String url = "http://localhost:9091/customer/12";
        String post = " {  \"id\": 9, \"firstName\": \"Adam\", \"lastName\": \"Adam\",  \"email\": \"email\",   \"status\": \"pp\" }";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.PUT, url);

        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());

    }

    @Test
    public void TestDeleteCustomerById() throws Exception {
        String url = "http://localhost:9091/customer/11";

        client = new Client(Protocol.HTTP);
        request = new Request(Method.DELETE, url);
        response = client.handle(request);
        assertEquals(204, response.getStatus().getCode());

    }

    @Test
    public void TestDeleteAll() throws Exception {
        String url = "http://localhost:9091/customer";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.DELETE, url);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
    }

}
