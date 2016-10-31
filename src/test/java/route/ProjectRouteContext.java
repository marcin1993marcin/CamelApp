package route;

import com.app.camel.routes.ProjectRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class ProjectRouteContext {

    private  final CamelContext context = new DefaultCamelContext();


    public void run() throws Exception {

        ProjectRoute projectRoute = new ProjectRoute();
        context.addRoutes(projectRoute);
        context.start();

    }

    public void stop() throws Exception {
        context.stop();
    }
}
