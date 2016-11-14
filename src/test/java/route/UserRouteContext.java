package route;

import com.app.camel.routes.UserRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import static configuration.Configuration.DATABASE_DRIVER;


public class UserRouteContext {

    private final CamelContext context = new DefaultCamelContext();

    public void run() throws Exception {


        Class.forName(DATABASE_DRIVER).newInstance();
        UserRoute userRoute = new UserRoute();

        context.addRoutes(userRoute);
        context.start();

    }

    public void stop() throws Exception {
        context.stop();
    }
}
