package route;

import com.app.camel.dao.impl.GenericRepository;
import com.app.camel.routes.UserRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static configuration.Configuration.DATABASE_DRIVER;


public class UserRouteContext extends GenericRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRouteContext.class);
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
