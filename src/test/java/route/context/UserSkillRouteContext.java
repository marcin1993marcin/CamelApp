package route.context;

import com.app.camel.routes.UserSkillRoute;

import static configuration.Configuration.DATABASE_DRIVER;

public class UserSkillRouteContext extends RouteContext {

    @Override
    public void run() throws Exception {
        Class.forName(DATABASE_DRIVER).newInstance();
        initializeDatabase();
        UserSkillRoute userSkillRoute = new UserSkillRoute();
        context.addRoutes(userSkillRoute);
        context.start();
    }
}
