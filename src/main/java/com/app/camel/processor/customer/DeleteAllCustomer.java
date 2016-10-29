package com.app.camel.processor.customer;

import com.app.camel.dao.CustomerRepository;
import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.CustomerRepositoryImpl;
import com.app.camel.dao.impl.UserRepositoryImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class DeleteAllCustomer implements Processor {

    private final static Logger logger = Logger.getLogger(DeleteAllCustomer.class);
    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {

        customerRepository.deleteAll();

        logger.info("Delete all customers success");
    }
}
