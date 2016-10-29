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
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.log4j.Logger;
import org.restlet.Response;
import org.restlet.data.Status;

public class InsertCustomer implements Processor {

    private final static Logger logger = Logger.getLogger(InsertCustomer.class);
    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String json = exchange.getIn().getBody(String.class);
        Customer customer = gson.fromJson(json, Customer.class);

        CustomerRecord customerRecord = new CustomerRecord();
        customerRecord.setLastName(customer.getLastName());
        customerRecord.setEmail(customer.getEmail());
        customerRecord.setFirstName(customer.getEmail());
        customerRecord.setStatus(customer.getStatus());
        Boolean status = customerRepository.insert(customerRecord);

        if (status) {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_CREATED);
            exchange.getOut().setBody(response);
            logger.info("Insert customer success");
        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
            exchange.getOut().setBody(response);
            logger.info("Insert customer failed");
        }


    }
}
