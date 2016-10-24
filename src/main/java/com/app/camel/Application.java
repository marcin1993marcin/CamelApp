package com.app.camel;

import com.app.camel.configuration.Config;
import com.app.camel.routes.ProjectRoute;
import com.app.camel.routes.UserRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class Application {
    public static void main(String[] args) throws Exception {

        Class.forName(Config.DRIVER).newInstance();
        CamelContext context = new DefaultCamelContext();

        UserRoute restfulRoute = new UserRoute();
        ProjectRoute projectRoute= new ProjectRoute();

        context.addRoutes(restfulRoute);
        context.addRoutes(projectRoute);

        context.start();
        Thread.sleep(5000000);
        context.stop();
    }
}