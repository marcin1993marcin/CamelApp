package route;

import com.app.camel.routes.ProjectRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class ProjectRouteContext {

    public void run() throws Exception
    {
        CamelContext context= new DefaultCamelContext();
        ProjectRoute projectRoute= new ProjectRoute();
        context.addRoutes(projectRoute);
        context.start();

    }
}
