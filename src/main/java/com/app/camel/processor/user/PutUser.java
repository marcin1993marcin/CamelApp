package com.app.camel.processor.user;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.User;
import com.app.camel.model.tables.records.UserRecord;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

public class PutUser implements Processor {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);

        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid user ID of value: \"" + id + "\"");

        String select = exchange.getIn().getBody(String.class);

        Preconditions.checkNotNull(select, "Body is null");

        User user = gson.fromJson(select, User.class);

        UserRecord userRecord = new UserRecord();
        userRecord.setId(Integer.parseInt(id));
        userRecord.setLastName(user.getLastName());
        userRecord.setEmail(user.getEmail());
        userRecord.setFirstName(user.getEmail());
        userRecord.setStatus(user.getStatus());


        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_CREATED);

        if (!userRepository.update(userRecord)) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);


    }
}
