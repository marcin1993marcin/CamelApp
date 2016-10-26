package com.app.camel.processor.project;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class DeleteAllProject implements Processor {

   private final ProjectRepository projectRepository = new ProjectRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {

        projectRepository.deleteAll();
    }
}
