package com.app.camel.processor.project;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import com.app.camel.dto.Project;
import com.app.camel.model.tables.records.ProjectRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SelectAllProject implements Processor {

    private final static Logger LOGGER = Logger.getLogger(SelectAllProject.class);
    private final ProjectRepository projectRepository = new ProjectRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        Collection<ProjectRecord> projects = projectRepository.getAll();

        List<Project> projectList = projects.stream().map(projectEntity -> Project.builder()
                .projectName(projectEntity.getProjectName())
                .build()
        ).collect(toList());

        exchange.getIn().setBody(gson.toJson(projectList));
        LOGGER.info("Select all projects success");
    }
}
