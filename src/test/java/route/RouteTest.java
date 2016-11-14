package route;

import org.restlet.Request;
import org.restlet.data.Method;

import static restconfiguration.RestConfiguration.REQUEST_URL;
import static restconfiguration.RestConfiguration.SEPARATOR;

public abstract class RouteTest {

    protected final String POST_REQUEST_CONTEXT;
    protected final String REQUEST_CONTEXT;

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
        return new Request(method, REQUEST_URL + REQUEST_CONTEXT + SEPARATOR + param + SEPARATOR
                + POST_REQUEST_CONTEXT + SEPARATOR + postParam);
    }
}
