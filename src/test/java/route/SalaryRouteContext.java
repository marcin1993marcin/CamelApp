package route;

import com.app.camel.routes.SalaryRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class SalaryRouteContext {

    private final CamelContext context = new DefaultCamelContext();

    public void run() throws Exception {
        SalaryRoute salaryRoute = new SalaryRoute();
        context.addRoutes(salaryRoute);
        context.start();
    }

    public void stop() throws Exception {
        context.stop();
    }
}
