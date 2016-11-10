package route;

import org.restlet.Request;
import org.restlet.data.Method;

import static restconfiguration.RestConfiguration.REQUEST_URL;
import static restconfiguration.RestConfiguration.SEPARATOR;

public abstract class RouteTest {

    protected final String REQUEST_CONTEXT;

    protected RouteTest(String requestContext) {
        REQUEST_CONTEXT = requestContext;
    }

    protected Request createRequest(Method method) {
        return createRequest(method, "");
    }

    protected Request createRequest(Method method, String url) {
        return new Request(method, REQUEST_URL + REQUEST_CONTEXT + SEPARATOR + url);
    }
}
