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

public class SalaryRouteTest {

    @Rule
    public ExpectedException exception =
            ExpectedException.none();
    private SalaryRouteContext salaryRouteContext = new SalaryRouteContext();
    private Client client = new Client(Protocol.HTTP);
    private Request request;
    private Response response;

    @Before
    public final void before() throws Exception {
        salaryRouteContext = new SalaryRouteContext();
        salaryRouteContext.run();
    }

    @After
    public void after() throws Exception {
        salaryRouteContext.stop();
    }

    @Test
    public void TestGetAllSalary() throws Exception {
        String url = "http://localhost:9091/salary";
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
    public void TestGetSalaryById() throws Exception {
        String url = "http://localhost:9091/salary/12";
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
    public void TestPostSalary() throws Exception {
        String url = "http://localhost:9091/salary";
        String post = " { \"userId\": 2, \"positionId\": 2,  \"monthly\": 100000,   \"perHour\": 500, " +
                "\"dateFrom\": 2016-01-01, \"dateTo\": 2016-10-22 }";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.POST, url);
        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(201, response.getStatus().getCode());
    }

    @Test
    public void TestPutSalaryById() throws Exception {
        String url = "http://localhost:9091/salary/12";
        String post = " { \"userId\": 2, \"positionId\": 2,  \"monthly\": 100000,   \"perHour\": 500, " +
                "\"dateFrom\": 2016-01-01, \"dateTo\": 2016-10-22 }";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.PUT, url);
        request.setEntity(post, MediaType.APPLICATION_ALL);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
    }

    @Test
    public void TestDeleteSalaryById() throws Exception {
        String url = "http://localhost:9091/salary/11";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.DELETE, url);
        response = client.handle(request);
        assertEquals(204, response.getStatus().getCode());
    }

    @Test
    public void TestDeleteAll() throws Exception {
        String url = "http://localhost:9091/salary";
        client = new Client(Protocol.HTTP);
        request = new Request(Method.DELETE, url);
        response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
    }

}
