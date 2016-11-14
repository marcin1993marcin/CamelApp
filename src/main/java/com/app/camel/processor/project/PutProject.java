package com.app.camel.processor.project;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import com.app.camel.dto.Project;
import com.app.camel.model.tables.records.ProjectRecord;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

public class PutProject implements Processor {

    private final ProjectRepository projectRepository = new ProjectRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();
    private static final String INVALID_PROJECT_ID = "Invalid user ID of value: ";

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);

        Preconditions.checkArgument(Precondition.isInteger(id), INVALID_PROJECT_ID + id);

        String select = exchange.getIn().getBody(String.class);

        Preconditions.checkNotNull(select, "Body is null");

        Project project = gson.fromJson(select, Project.class);
        ProjectRecord projectRecord = new ProjectRecord();
        projectRecord.setProjectName(project.getProjectName());
        projectRecord.setId(Integer.parseInt(id));

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_CREATED);

        if (!projectRepository.update(projectRecord)) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);

    }

}
