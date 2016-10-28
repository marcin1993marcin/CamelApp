package com.app.camel.processor.project;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class DeleteAllProject implements Processor {

   private final static Logger LOGGER = Logger.getLogger(DeleteAllProject.class);
   private final ProjectRepository projectRepository = new ProjectRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {

        projectRepository.deleteAll();
        LOGGER.info("Delete all users success");
    }
}
