package route;

import com.app.camel.routes.CustomerRoute;
import org.junit.*;
import org.restlet.Request;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import static org.junit.Assert.assertEquals;

public class CustomerRouteTest extends RouteTest {

    private static final RouteContext routeContext = new RouteContext(new CustomerRoute());

    public CustomerRouteTest() {
        super("customer");
    }

    @BeforeClass
    public static void beforeClass() throws Exception{
        routeContext.run();
    }

    @AfterClass
    public static void afterClass() throws Exception{
        routeContext.stop();
    }

    @Test
    public void TestGetAllCustomer() throws Exception{
        String url = "http://localhost:9091/customer";
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
        request = new Request(Method.POST, url);

        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(201, response.getStatus().getCode());

    }

    @Test
    public void TestPutCustomerById() throws Exception {

        String url = "http://localhost:9091/customer/12";
        String post = " {  \"id\": 9, \"firstName\": \"Adam\", \"lastName\": \"Adam\",  \"email\": \"email\",   \"status\": \"pp\" }";
        request = new Request(Method.PUT, url);

        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());

    }

    @Test
    public void TestDeleteCustomerById() throws Exception {
        String url = "http://localhost:9091/customer/11";

        request = new Request(Method.DELETE, url);
        response = client.handle(request);
        assertEquals(204, response.getStatus().getCode());

    }

    @Test
    public void TestDeleteAll() throws Exception {
        String url = "http://localhost:9091/customer";
        request = new Request(Method.DELETE, url);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
    }

}
