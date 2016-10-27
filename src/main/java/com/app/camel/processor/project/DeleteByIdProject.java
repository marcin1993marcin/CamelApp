package com.app.camel.processor.project;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.log4j.Logger;
import org.restlet.Response;
import org.restlet.data.Status;

public class DeleteByIdProject implements Processor {

    private final static Logger logger = Logger.getLogger(DeleteAllProject.class);
    private final ProjectRepository projectRepository = new ProjectRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);
        projectRepository.delete(Integer.parseInt(id));

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_NO_CONTENT);
        exchange.getOut().setBody(response);
        logger.info("Delete project by id: " + id + " success");
    }
}
