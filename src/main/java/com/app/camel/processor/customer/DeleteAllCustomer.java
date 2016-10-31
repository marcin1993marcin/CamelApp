package com.app.camel.processor.customer;

import com.app.camel.dao.CustomerRepository;
import com.app.camel.dao.impl.CustomerRepositoryImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteAllCustomer implements Processor {

    private final static Logger logger = LoggerFactory.getLogger(DeleteAllCustomer.class);
    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {

        try{
            customerRepository.deleteAll();
        }catch (Exception e){
            logger.warn("Could not delete all customers");
        }
        logger.info("Delete all customers success");

    }
}
