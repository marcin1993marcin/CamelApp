package com.app.camel.processor.project;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.camel.component.restlet.RestletConstants.RESTLET_RESPONSE;
import static org.restlet.data.Status.REDIRECTION_NOT_MODIFIED;
import static org.restlet.data.Status.SUCCESS_NO_CONTENT;

public class DeleteProject implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteProject.class);
    private final ProjectRepository projectRepository = new ProjectRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid project ID of value: \"" + id + "\"");

        Response response = exchange.getIn().getHeader(RESTLET_RESPONSE, Response.class);
        response.setStatus(SUCCESS_NO_CONTENT);

        if(!projectRepository.delete(Integer.parseInt(id))) {
            response.setStatus(REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);

    }
}
