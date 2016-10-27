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

import java.util.Optional;

public class SelectByIdUser implements Processor {

    private final static Logger logger = Logger.getLogger(SelectAllUser.class);
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        Optional<UserRecord> userRecord = userRepository.get(Integer.parseInt(id));

        if (userRecord.isPresent()) {
            User user = User.builder()
                    .id(userRecord.get().getId())
                    .firstName(userRecord.get().getFirstName())
                    .email(userRecord.get().getEmail())
                    .status(userRecord.get().getStatus())
                    .lastName(userRecord.get().getLastName())
                    .build();

            exchange.getIn().setBody(gson.toJson(user));
            logger.info("select user by id: " + id + "success");
        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_NO_CONTENT);
            exchange.getOut().setBody(response);
            logger.info("user not exists");
        }
    }
}
