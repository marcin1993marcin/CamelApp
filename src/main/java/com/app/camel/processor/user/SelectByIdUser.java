package com.app.camel.processor.user;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.User;
import com.app.camel.model.tables.records.UserRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SelectByIdUser implements Processor {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        UserRecord userRecord= userRepository.get(Integer.parseInt(id));
        User user= new User(userRecord.getId(), userRecord.getFirstName(), userRecord.getLastName(), userRecord.getEmail(), userRecord.getStatus());
        String json= gson.toJson(user);
        exchange.getIn().setBody(json);
    }
}
