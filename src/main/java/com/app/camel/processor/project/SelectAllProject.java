package com.app.camel.processor.project;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import com.app.camel.dto.Project;
import com.app.camel.model.tables.records.ProjectRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectAllProject implements Processor {

    private final ProjectRepository projectRepository = new ProjectRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        Collection<ProjectRecord> projects = projectRepository.getAll();

        List<Project> projectlist = new ArrayList<Project>();
        for (ProjectRecord projectRecord : projects) {
            Project project = new Project(projectRecord.getId(), projectRecord.getProjectName());
            projectlist.add(project);
        }
        String json = gson.toJson(projectlist);
        exchange.getIn().setBody(json);
    }
}
