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

import java.util.Optional;

public class SelectProject implements Processor {

    private final ProjectRepository projectRepository = new ProjectRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();
    private static final String INVALID_PROJECT_ID = "Invalid user ID of value: ";

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(id), INVALID_PROJECT_ID + id);

        Optional<ProjectRecord> projectRecord = projectRepository.get(Integer.parseInt(id));

        if (projectRecord.isPresent()) {
            Project project = Project.builder()
                    .id(Long.valueOf(projectRecord.get().getId()))
                    .projectName(projectRecord.get().getProjectName())
                    .build();
            String json = gson.toJson(project);
            exchange.getIn().setBody(json);
        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_NO_CONTENT);
            exchange.getOut().setBody(response);
        }

    }
}

