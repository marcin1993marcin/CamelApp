package com.app.camel.processor.user;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.model.tables.records.UserRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Collection;

/**
 * Created by britenet on 2016-10-24.
 */
public class SelectAll implements Processor {

    UserRepository userRepository= new UserRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {
        Collection<UserRecord> body = userRepository.getAll();
        exchange.getIn().setBody(body);
        System.out.println(body);

    }
}
