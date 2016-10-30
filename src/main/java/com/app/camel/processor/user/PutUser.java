package com.app.camel.processor.user;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.User;
import com.app.camel.model.tables.records.UserRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class PutUser implements Processor {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();
    private final static Logger LOGGER = Logger.getLogger(PutUser.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        String json = exchange.getIn().getBody(String.class);

        // review a co jeżeli wystąpi JsonSyntaxException
        User user = gson.fromJson(json, User.class);

        UserRecord userRecord = new UserRecord();
        // review a co jeżeli id nie da się przeparsować?
        userRecord.setId(Integer.parseInt(id));
        userRecord.setLastName(user.getLastName());
        userRecord.setEmail(user.getEmail());
        userRecord.setFirstName(user.getEmail());
        userRecord.setStatus(user.getStatus());

        // review - zwraca boolean  nie sprawdzacie czy się udało?
        userRepository.update(userRecord);

        // review - z powodu wyjątku może się nic nie zalogować
        LOGGER.info("Put user by id " + id + "success");

    }
}
