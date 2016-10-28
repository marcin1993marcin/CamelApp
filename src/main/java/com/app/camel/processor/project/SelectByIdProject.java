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
import org.apache.log4j.Logger;
import org.restlet.Response;
import org.restlet.data.Status;

import java.util.Optional;


public class SelectByIdProject implements Processor {

    private final static Logger LOGGER = Logger.getLogger(SelectAllProject.class);
    private final ProjectRepository projectRepository = new ProjectRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);
        Optional<ProjectRecord> projectRecord = projectRepository.get(Integer.parseInt(id));
        if (projectRecord.isPresent()) {
            Project project = Project.builder()
                    .id(Long.valueOf(projectRecord.get().getId()))
                    .projectName(projectRecord.get().getProjectName())
                    .build();
            String json = gson.toJson(project);
            exchange.getIn().setBody(json);
            LOGGER.info("Select project by id " + id + " success");
        }
        else{
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_NO_CONTENT);
            exchange.getOut().setBody(response);

        }
    }
    }

