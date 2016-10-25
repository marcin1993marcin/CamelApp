package com.app.camel.processor.user;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.User;
import com.app.camel.model.tables.records.UserRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectAllUser implements Processor {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        Collection<UserRecord> users= userRepository.getAll();
        List<User> userList=new ArrayList<User>();
        for(UserRecord userRecord: users)
        {
            User user= new User(userRecord.getId(), userRecord.getFirstName(), userRecord.getLastName(), userRecord.getEmail(), userRecord.getStatus());
            userList.add(user);
        }
        String json= gson.toJson(userList);
        exchange.getIn().setBody(json);
    }
}
