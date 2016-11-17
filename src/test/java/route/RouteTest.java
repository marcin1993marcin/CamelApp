package route;

import databaseoperation.Migrate;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import util.ReadResources;

import static restconfiguration.RestConfiguration.REQUEST_URL;
import static restconfiguration.RestConfiguration.SEPARATOR;

public abstract class RouteTest {

    @Rule
    public ExpectedException exception =
            ExpectedException.none();
    protected Request request;
    protected Response response;

    protected final String POST_REQUEST_CONTEXT;
    protected final String REQUEST_CONTEXT;
    protected final Client client = new Client(Protocol.HTTP);
    protected final ReadResources readResources = new ReadResources();
    protected final Migrate migrate = new Migrate();

    protected RouteTest(String requestContext) {
        this(requestContext, "");
    }

    protected RouteTest(String REQUEST_CONTEXT, String POST_REQUEST_CONTEXT) {
        this.POST_REQUEST_CONTEXT = POST_REQUEST_CONTEXT;
        this.REQUEST_CONTEXT = REQUEST_CONTEXT;
    }

    protected Request createRequest(Method method) {
        return createRequest(method, "");
    }

    protected Request createRequest(Method method, String param) {
        return createRequest(method, param, "");
    }

    protected Request createRequest(Method method, String param, String postParam) {
        StringBuilder sb = new StringBuilder();
        sb.append(REQUEST_URL);
        sb.append(REQUEST_CONTEXT);
        if(!param.equals("")) {
            sb.append(SEPARATOR);
            sb.append(param);
        }
        if(!POST_REQUEST_CONTEXT.equals("")) {
            sb.append(SEPARATOR);
            sb.append(POST_REQUEST_CONTEXT);
        }
        if(!postParam.equals("")) {
            sb.append(SEPARATOR);
            sb.append(postParam);
        }
        return new Request(method, sb.toString());
    }
}
