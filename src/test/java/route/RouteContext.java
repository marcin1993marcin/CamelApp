package route;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import static configuration.Configuration.DATABASE_DRIVER;

public class RouteContext {

    protected final CamelContext context = new DefaultCamelContext();
    private RouteBuilder route;

    public RouteContext(RouteBuilder route) {
        this.route = route;
    }

    public void run() throws Exception {
        Class.forName(DATABASE_DRIVER).newInstance();
        context.addRoutes(route);
        context.start();
    }

    public void stop() throws Exception {
        context.stop();
    }
}
