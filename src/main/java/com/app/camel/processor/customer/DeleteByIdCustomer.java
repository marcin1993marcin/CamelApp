package com.app.camel.processor.customer;

import com.app.camel.dao.CustomerRepository;
import com.app.camel.dao.UserRepository;
import com.app.camel.dao.impl.CustomerRepositoryImpl;
import com.app.camel.dao.impl.UserRepositoryImpl;
import com.google.common.base.Preconditions;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.log4j.Logger;
import org.restlet.Response;
import org.restlet.data.Status;

public class DeleteByIdCustomer implements Processor {

    private final static Logger logger = Logger.getLogger(DeleteByIdCustomer.class);
    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);
        customerRepository.delete(Integer.parseInt(id));
        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_NO_CONTENT);
        exchange.getOut().setBody(response);
        logger.info("Delete customer by id "+ id +" success");

    }
}
