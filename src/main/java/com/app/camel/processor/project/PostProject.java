package com.app.camel.processor.project;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import com.app.camel.dto.Project;
import com.app.camel.model.tables.records.ProjectRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

public class PostProject implements Processor {

    private final ProjectRepository projectRepository = new ProjectRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String select = exchange.getIn().getBody(String.class);
        Project project = gson.fromJson(select, Project.class);

        ProjectRecord projectRecord = new ProjectRecord();
        projectRecord.setProjectName(project.getProjectName());
        projectRepository.insert(projectRecord);


        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_CREATED);
        exchange.getOut().setBody(response);
    }
}
