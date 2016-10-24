package com.app.camel.routes;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.User;
import com.app.camel.dto.UserStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

public class UserRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        final UserRepository userRepository = new UserRepositoryImpl();
        final Gson gson = new GsonBuilder().create();

        from("restlet:http://localhost:9091/user?restletMethod=get").to("direct:select");
        from("restlet:http://localhost:9091/user/{id}?restletMethod=get").to("direct:idSelect");
        from("restlet:http://localhost:9091/user?restletMethod=post").to("direct:post");
        from("restlet:http://localhost:9091/user?restletMethod=put").to("direct:put");
        from("restlet:http://localhost:9091/user/{id}?restletMethod=put").to("direct:putId");
        from("restlet:http://localhost:9091/user?restletMethod=delete").to("direct:delete");
        from("restlet:http://localhost:9091/user/{id}?restletMethod=delete").to("direct:deleteId");

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

                String id = exchange.getIn().getHeader("id", String.class);
                String body = userRepository.getUserById(Integer.parseInt(id));
                exchange.getIn().setBody(body);
            }
        }).transform().body();

        from("direct:post")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String json = exchange.getIn().getBody(String.class);
                        User user = gson.fromJson(json, User.class);
                        UserStatus status = UserStatus.ACTIVE;
                        if (user.getIsActive() == 0) {
                            status = UserStatus.DISABLED;
                        }

                        userRepository.addUser(user.getFirstName(), user.getLastName(), user.getEmail(), status);

                        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
                        response.setStatus(Status.SUCCESS_CREATED);
                        exchange.getOut().setBody(response);

                    }
                }).transform().body();
        from("direct:put")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                    }
                });
        from("direct:putId")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String id = exchange.getIn().getHeader("id", String.class);
                        String json = exchange.getIn().getBody(String.class);
                        User user = gson.fromJson(json, User.class);

                        UserStatus status = UserStatus.ACTIVE;
                        if (user.getIsActive() == 0) {
                            status = UserStatus.DISABLED;
                        }
                        userRepository.updateUserWithId(Integer.parseInt(id), user.getFirstName(), user.getLastName(), user.getEmail(), status);
                    }
                });
        from("direct:delete")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        //delete all
                    }
                });
        from("direct:deleteId")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String id = exchange.getIn().getHeader("id", String.class);
                        userRepository.deleteUser(Integer.parseInt(id));

                        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
                        response.setStatus(Status.SUCCESS_NO_CONTENT);
                        exchange.getOut().setBody(response);
                    }
                }).transform().body();
    }
}