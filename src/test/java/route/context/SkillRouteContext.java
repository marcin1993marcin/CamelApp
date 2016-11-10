package route.context;

import com.app.camel.routes.SkillRoute;

import static configuration.Configuration.DATABASE_DRIVER;

public class SkillRouteContext extends RouteContext {

    @Override
    public void run() throws Exception {
        Class.forName(DATABASE_DRIVER).newInstance();
        initializeDatabase();
        SkillRoute skillRoute = new SkillRoute();
        context.addRoutes(skillRoute);
        context.start();
    }
}
