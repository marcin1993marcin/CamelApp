package route;

import com.app.camel.Application;
import org.junit.Assert;
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

import static org.junit.Assert.assertEquals;

public class ProjectRouteTest {



    @Rule
    public ExpectedException exception =
            ExpectedException.none();
    private ProjectRouteContext projectRouteContext = new ProjectRouteContext();

    @Before
    public final void before()
    {
        projectRouteContext= new ProjectRouteContext();
    }

    @Test
    public void TestGetAllUser() throws Exception {

        projectRouteContext.run();
        String url ="http://localhost:9091/project";
        Client client = new Client(Protocol.HTTP);
        Request request = new Request(Method.GET, url);

        Response response = client.handle(request);
        assertEquals(200, response.getStatus().getCode());
        Assert.assertTrue(response.isEntityAvailable());
        Assert. assertEquals(MediaType.TEXT_PLAIN, response.getEntity().getMediaType());
        String responseString = response.getEntityAsText();
        Assert.assertNotNull(responseString);
    }

}
