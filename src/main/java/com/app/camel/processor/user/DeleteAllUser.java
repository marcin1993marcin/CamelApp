package com.app.camel.processor.user;

import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.UserRepositoryImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class DeleteAllUser implements Processor {

    private final static Logger LOGGER = Logger.getLogger(DeleteAllUser.class);
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {

        // review - nie sprawdzacie czy się udało usunąc?
        userRepository.deleteAll();

        // review - a co jeżeli nie będzie success?
        LOGGER.info("Delete all users success");
    }
}
