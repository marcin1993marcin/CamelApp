package route;

import com.app.camel.routes.UserRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class UserRouteContext
{
    CamelContext context= new DefaultCamelContext();

    public void run() throws Exception {
        UserRoute userRoute = new UserRoute();
        context.addRoutes(userRoute);
        context.start();
    }

    public void stop() throws Exception {
        context.stop();
    }
}
