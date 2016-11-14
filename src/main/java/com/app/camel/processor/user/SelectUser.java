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

import java.util.Optional;

import static java.lang.Long.valueOf;

public class SelectUser implements Processor {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();
    private static final String INVALID_USER_ID = "Invalid user ID of value: ";


    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(id), INVALID_USER_ID + id);

        Optional<UserRecord> userRecord = userRepository.get(Integer.parseInt(id));

        if (userRecord.isPresent()) {
            User user = User.builder()
                    .id(valueOf(userRecord.get().getId()))
                    .firstName(userRecord.get().getFirstName())
                    .email(userRecord.get().getEmail())
                    .status(userRecord.get().getStatus())
                    .lastName(userRecord.get().getLastName())
                    .build();

            exchange.getIn().setBody(gson.toJson(user));

        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_NO_CONTENT);
            exchange.getOut().setBody(response);
        }
    }
}
