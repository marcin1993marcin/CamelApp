package com.app.camel.processor.customer;

import com.app.camel.dao.CustomerRepository;
import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.CustomerRepositoryImpl;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.Customer;
import com.app.camel.dto.User;
import com.app.camel.model.tables.records.CustomerRecord;
import com.app.camel.model.tables.records.UserRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class PutCustomer implements Processor {

    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();
    private final static Logger logger = Logger.getLogger(PutCustomer.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        String json = exchange.getIn().getBody(String.class);
        Customer customer = gson.fromJson(json, Customer.class);

        CustomerRecord customerRecord = new CustomerRecord();
        customerRecord.setId(Integer.parseInt(id));
        customerRecord.setLastName(customer.getLastName());
        customerRecord.setEmail(customer.getEmail());
        customerRecord.setFirstName(customer.getEmail());
        customerRecord.setStatus(customer.getStatus());
        customerRepository.update(customerRecord);
        logger.info("put customer by id " + id + "success");

    }
}
