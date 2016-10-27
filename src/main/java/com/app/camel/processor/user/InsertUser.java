package com.app.camel.processor.user;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.User;
import com.app.camel.model.tables.records.UserRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.log4j.Logger;
import org.restlet.Response;
import org.restlet.data.Status;

public class InsertUser implements Processor {

    private final static Logger logger = Logger.getLogger(InsertUser.class);
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String json = exchange.getIn().getBody(String.class);
        User user = gson.fromJson(json, User.class);

        UserRecord userRecord = new UserRecord();
        userRecord.setLastName(user.getLastName());
        userRecord.setEmail(user.getEmail());
        userRecord.setFirstName(user.getEmail());
        userRecord.setStatus(user.getStatus());
        userRepository.insert(userRecord);

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_CREATED);
        exchange.getOut().setBody(response);
        logger.info("Insert user success");
    }
}
