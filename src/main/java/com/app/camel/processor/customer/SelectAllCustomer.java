package com.app.camel.processor.customer;

import com.app.camel.dao.CustomerRepository;
import com.app.camel.dao.impl.CustomerRepositoryImpl;
import com.app.camel.dto.Customer;
import com.app.camel.dto.User;
import com.app.camel.model.tables.records.CustomerRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SelectAllCustomer implements Processor {

    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();
    private final static Logger logger = Logger.getLogger(SelectAllCustomer.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Collection<CustomerRecord> customers = customerRepository.getAll();

        List<Customer> customerList = customers.stream().map(customerEntity -> Customer.builder()
                .id(customerEntity.getId())
                .firstName(customerEntity.getFirstName())
                .build()
        ).collect(toList());
        logger.info("Processing all customers");
        exchange.getIn().setBody(gson.toJson(customerList));
    }
}
