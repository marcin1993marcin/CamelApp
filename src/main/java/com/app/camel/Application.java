package com.app.camel;

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
        Thread.sleep(500000);
        context.stop();
    }
}
