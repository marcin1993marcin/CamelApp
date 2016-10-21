package com.app.camel;

import com.app.camel.DAO.UserRepository;
import com.app.camel.DAO.UserRepositoryImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class RestfulRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        final UserRepository userRepository = new UserRepositoryImpl();
        from("restlet:http://localhost:9091/user?restletMethod=get").to("direct:select");
        from("restlet:http://localhost:9091/user/{id}?restletMethod=get").to("direct:idSelect");
        from("restlet:http://localhost:9091/user?restletMethod=put").to("direct:select").transform().simple("put ");

        from("direct:select").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                String body = userRepository.getAllUsers();
                exchange.getIn().setBody(body);
            }
        }).transform().body();

        from("direct:idSelect").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                String uri = exchange.getIn().getHeader(Exchange.HTTP_URI, String.class);
                String[] test = uri.split("/");
                String id = test[test.length - 1];

                int idint=Integer.parseInt(id);
                System.out.println(idint);
                String body = userRepository.getUserById(idint);
                exchange.getIn().setBody(body);
            }
        }).transform().body();
    }
}