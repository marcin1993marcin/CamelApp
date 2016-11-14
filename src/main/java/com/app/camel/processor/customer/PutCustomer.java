package com.app.camel.processor.customer;

import com.app.camel.dao.CustomerRepository;
import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.CustomerRepositoryImpl;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.app.camel.dto.Customer;
import com.app.camel.dto.User;
import com.app.camel.model.tables.records.CustomerRecord;
import com.app.camel.model.tables.records.UserRecord;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.log4j.Logger;
import org.restlet.Response;
import org.restlet.data.Status;

public class PutCustomer implements Processor {

    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid user ID of value: " + id);

        String json = exchange.getIn().getBody(String.class);
        Preconditions.checkNotNull(json, "Body is null");
        Customer customer = gson.fromJson(json, Customer.class);

        CustomerRecord customerRecord = new CustomerRecord();
        customerRecord.setId(Integer.parseInt(id));
        customerRecord.setLastName(customer.getLastName());
        customerRecord.setEmail(customer.getEmail());
        customerRecord.setFirstName(customer.getEmail());
        customerRecord.setStatus(customer.getStatus());

        Preconditions.checkNotNull(customerRecord.getStatus(), "Wrong user status");

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_CREATED);

        if (!customerRepository.update(customerRecord)) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);

    }
}
