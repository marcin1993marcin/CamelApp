package route;

import com.app.camel.routes.PositionRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class PositionRouteContext {

    private final CamelContext context = new DefaultCamelContext();

    public void run() throws Exception {

        PositionRoute positionRoute = new PositionRoute();
        context.addRoutes(positionRoute);
        context.start();
    }

    public void stop() throws Exception {
        context.stop();
    }
}
