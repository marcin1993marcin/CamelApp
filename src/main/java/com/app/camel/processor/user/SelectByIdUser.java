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

    private final static Logger LOGGER = Logger.getLogger(SelectAllUser.class);
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        // review - logowanie parametrów przychodzacych - tutaj i w innych processorach
        String id = exchange.getIn().getHeader("id", String.class);
        Optional<UserRecord> userRecord = userRepository.get(Integer.parseInt(id));

        if (userRecord.isPresent()) {
            User user = User.builder()
                    .id(Long.valueOf(userRecord.get().getId()))
                    .firstName(userRecord.get().getFirstName())
                    .email(userRecord.get().getEmail())
                    .status(userRecord.get().getStatus())
                    .lastName(userRecord.get().getLastName())
                    .build();

            exchange.getIn().setBody(gson.toJson(user));
            LOGGER.info("Select user by id: " + id + "success");
            // review dałbym również na poziomie debug całego JSON'a
        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_NO_CONTENT);
            exchange.getOut().setBody(response);
            LOGGER.info("User not exists");
        }
    }
}
