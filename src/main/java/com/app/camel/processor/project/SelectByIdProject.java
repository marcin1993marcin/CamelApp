package com.app.camel.processor.project;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import com.app.camel.dto.Project;
import com.app.camel.model.tables.records.ProjectRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class SelectByIdProject implements Processor {

    private final ProjectRepository projectRepository = new ProjectRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);
        ProjectRecord projectRecord = projectRepository.get(Integer.parseInt(id));
        Project project = new Project(projectRecord.getId(), projectRecord.getProjectName());
        String json = gson.toJson(project);
        exchange.getIn().setBody(json);

    }
}
