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

    private final static Logger LOGGER = Logger.getLogger(InsertUser.class);
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String json = exchange.getIn().getBody(String.class);
        User user = gson.fromJson(json, User.class);

        // review - what if user is null? możliwe będzie?
        // review - a co jeżeli  unmarszaling się nie uda? co się stanie?

        UserRecord userRecord = new UserRecord();
        userRecord.setLastName(user.getLastName());
        userRecord.setEmail(user.getEmail());
        userRecord.setFirstName(user.getEmail());
        userRecord.setStatus(user.getStatus());

        // review - dlaczego duży boolean a nie mały
        Boolean status = userRepository.insert(userRecord);


        // review dlaczego od razu nie wywołac userRepository.insert(userRecord)
        if (status) {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_CREATED);
            exchange.getOut().setBody(response);

            // review - A jak później znajdziecie w logach jaki użytkownik się dodał? - moze ta linia powinna byc na poczatu?
            LOGGER.info("Insert user success");
        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
            exchange.getOut().setBody(response);

            // review - response error a LOGGER info?
            // review - a jak później znajdziecie więcej informacji o tym błędzie? DLa jakiego użytkonika, jakich danych błąd wystąpił?
            // review - tutaj raczej poziom WARN lub nawet ERROR
            LOGGER.info("Insert user failed");
        }


    }
}
