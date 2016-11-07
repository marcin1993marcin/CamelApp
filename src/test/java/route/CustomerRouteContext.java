package route;

import com.app.camel.routes.CustomerRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;


public class CustomerRouteContext {

    CamelContext context = new DefaultCamelContext();

    public void run() throws Exception{
        CustomerRoute customerRoute = new CustomerRoute();
        context.addRoutes(customerRoute);
        context.start();
    }

    public void stop() throws Exception{
        context.stop();
    }
}
