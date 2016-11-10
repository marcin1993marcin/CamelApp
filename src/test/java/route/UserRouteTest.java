package route;

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
import static restconfiguration.RestConfiguration.*;

public class UserRouteTest {

    private static final String REQUEST_CONTEXT = "user";

    @Rule
    public ExpectedException exception =
            ExpectedException.none();
    private UserRouteContext userRouteContext = new UserRouteContext();
    private Client client = new Client(Protocol.HTTP);
    private Request request;
    private Response response;
    private ReadResources readResources = new ReadResources();

    @Before
    public final void before() throws Exception {
        userRouteContext = new UserRouteContext();
        userRouteContext.run();
        client = new Client(Protocol.HTTP);
    }

    @After
    public void after() throws Exception {
        userRouteContext.stop();
    }

    @Test
    public void shouldReturnUsers() throws Exception {

        //given
        request = createRequest(Method.GET, "");

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Returned users list").isEqualTo(SUCCESS_OK);

    }


    @Test
    public void shouldReturnUserById() throws Exception {

        //given
        String url = "/12";
        request = createRequest(Method.GET, url);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Return user by id %s", url).isEqualTo(SUCCESS_OK);
    }

    @Test
    public void shouldNotReturnUserById() throws Exception {

        //given
        String url = "/13";
        request = createRequest(Method.GET, url);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("User with id  %s not exist", url).isEqualTo(SUCCESS_NO_CONTENT);
    }

    @Test
    public void shouldCreateUserInDatabase() throws Exception {

        //given
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "correctlyPostRequestBody.json");
        request = createRequest(Method.POST, "");
        request.setEntity(post, MediaType.APPLICATION_ALL);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("User created ").isEqualTo(SUCCESS_CREATED);

    }

    @Test
    public void shouldNotUpdateUserWithInvalidStatus() throws Exception {

        // given
        String url = "/12";
        String post = readResources.readFile(REQUEST_JSON_LOCATION + "invalidStatusRequestBody.json");
        request = createRequest(Method.PUT, url);
        request.setEntity(post, MediaType.APPLICATION_ALL);

        //when
        response = client.handle(request);

        // then
        assertThat(response.getStatus()).as("returned server response").isEqualTo(CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void shouldDeleteUserById() throws Exception {

        //given
        String url = "/11";
        request = createRequest(Method.DELETE, url);

        //when
        response = client.handle(request);

        //then
        assertThat(response.getStatus()).as("Return user by id %s", url).isEqualTo(SUCCESS_NO_CONTENT);

    }

    @Test
    public void shouldDeleteUsersD() throws Exception {

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
