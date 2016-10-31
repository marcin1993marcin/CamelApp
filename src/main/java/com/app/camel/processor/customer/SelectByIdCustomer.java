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

import java.util.Optional;

public class SelectByIdCustomer implements Processor {

    private final static Logger logger = Logger.getLogger(SelectByIdCustomer.class);
    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        Optional<CustomerRecord> customerRecord = customerRepository.get(Integer.parseInt(id));

        if (customerRecord.isPresent()) {
            Customer customer = Customer.builder()
                    .id(customerRecord.get().getId())
                    .firstName(customerRecord.get().getFirstName())
                    .email(customerRecord.get().getEmail())
                    .status(customerRecord.get().getStatus())
                    .lastName(customerRecord.get().getLastName())
                    .build();

            exchange.getIn().setBody(gson.toJson(customer));
            logger.info("select customer by id: " + id + "success");
        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_NO_CONTENT);
            exchange.getOut().setBody(response);
            logger.info("customer does not exists");
        }
    }
}
