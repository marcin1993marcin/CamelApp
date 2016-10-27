package com.app.camel.processor.user;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class DeleteAllUser implements Processor {

    private final static Logger logger = Logger.getLogger(DeleteAllUser.class);
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {

        userRepository.deleteAll();

        logger.info("Delete all users success");
    }
}
