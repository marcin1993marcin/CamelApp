package com.app.camel.routes;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.User;
import com.app.camel.dto.UserStatus;
import com.app.camel.model.tables.records.UserRecord;
import com.app.camel.processor.user.SelectAll;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UserRoute extends RouteBuilder {
    public static final String Uri = "restlet:http://localhost:9091/user";

    @Override
    public void configure() throws Exception {
        final UserRepository userRepository = new UserRepositoryImpl();
        final Gson gson = new GsonBuilder().create();

        from(Uri + "?restletMethod=get").to("direct:select");
        from(Uri + "/{id}?restletMethod=get").to("direct:idSelect");
        from(Uri + "?restletMethod=post").to("direct:post");
        from(Uri + "?restletMethod=put").to("direct:put");
        from(Uri + "/{id}?restletMethod=put").to("direct:putId");
        from(Uri + "?restletMethod=delete").to("direct:delete");
        from(Uri + "/{id}?restletMethod=delete").to("direct:deleteId");

        from("direct:select").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                Collection<UserRecord> users= userRepository.getAll();
                List<User> userlist=new ArrayList<User>();
                for(UserRecord userRecord: users)
                {
                    User user= new User(userRecord.getId(), userRecord.getFirstName(), userRecord.getLastName(), userRecord.getEmail(), userRecord.getStatus());
                    userlist.add(user);
                }
                String json= gson.toJson(userlist);
                exchange.getIn().setBody(json);
            }
        })
        .transform().body();

        from("direct:idSelect").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {

                String id = exchange.getIn().getHeader("id", String.class);
                UserRecord userRecord= userRepository.get(Integer.parseInt(id));
                User user= new User(userRecord.getId(), userRecord.getFirstName(), userRecord.getLastName(), userRecord.getEmail(), userRecord.getStatus());
                String json= gson.toJson(user);
                exchange.getIn().setBody(json);

            }
        }).transform().body();

        from("direct:post")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String json = exchange.getIn().getBody(String.class);
                        User user = gson.fromJson(json, User.class);

                        UserRecord userRecord= new UserRecord();
                        userRecord.setLastName(user.getLastName());
                        userRecord.setEmail(user.getEmail());
                        userRecord.setFirstName(user.getEmail());
                        userRecord.setStatus(user.getStatus());
                        userRepository.insert(userRecord);

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

                        UserRecord userRecord= new UserRecord();
                        userRecord.setId(Integer.parseInt(id));
                        userRecord.setLastName(user.getLastName());
                        userRecord.setEmail(user.getEmail());
                        userRecord.setFirstName(user.getEmail());
                        userRecord.setStatus(user.getStatus());
                        userRepository.update(userRecord);

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
                        userRepository.delete(Integer.parseInt(id));
                        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
                        response.setStatus(Status.SUCCESS_NO_CONTENT);
                        exchange.getOut().setBody(response);
                    }
                }).transform().body();
    }
}