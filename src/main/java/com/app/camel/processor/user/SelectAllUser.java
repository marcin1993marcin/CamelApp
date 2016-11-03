package com.app.camel.processor.user;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.User;
import com.app.camel.model.tables.records.UserRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SelectAllUser implements Processor {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        Collection<UserRecord> users = userRepository.getAll();

        List<User> userList = users.stream().map(userEntity -> User.builder()
                .id(Long.valueOf(userEntity.getId()))
                .firstName(userEntity.getFirstName())
                .status(userEntity.getStatus())
                .lastName(userEntity.getLastName())
                .build()
        ).collect(toList());

        exchange.getIn().setBody(gson.toJson(userList));
    }
}
