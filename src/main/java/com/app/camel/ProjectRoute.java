package com.app.camel;

import com.app.camel.DAO.ProjectRepository;
import com.app.camel.DAO.ProjectRepositoryImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by britenet on 2016-10-21.
 */
public class ProjectRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        final ProjectRepository projectRepository= new ProjectRepositoryImpl();
        final Gson gson = new GsonBuilder().create();

        from("restlet:http://localhost:9091/project?restletMethod=get").to("direct:projectSelect");
        from("restlet:http://localhost:9091/project/{id}?restletMethod=get").to("direct:projectSelectId");
        from("restlet:http://localhost:9091/project?restletMethod=post").to("direct:projectPost");
        from("restlet:http://localhost:9091/project?restletMethod=put").to("direct:projectPut");
        from("restlet:http://localhost:9091/project/{id}?restletMethod=put").to("direct:projectPutId");
        from("restlet:http://localhost:9091/project?restletMethod=delete").to("direct:projectDelete");
        from("restlet:http://localhost:9091/project/{id}?restletMethod=delete").to("direct:projectDeleteId");

        from("direct:projectSelect")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String select= projectRepository.getAllProjects();
                        exchange.getIn().setBody(select);

                    }
                })
                .transform().body();
        from("direct:projectSelectId")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String id =exchange.getIn().getHeader("id", String.class);
                        String select=projectRepository.getProjectById(Integer.parseInt(id));
                        exchange.getIn().setBody(select);
                    }
                })
                .transform().body();
        from("direct:projectPost")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String select= exchange.getIn().getBody(String.class);
                        Project project= gson.fromJson(select, Project.class);
                        projectRepository.addProject(project.getProjectName());

                    }
                });
        from("direct:projectPut")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                    }
                });
        from("direct:projectPutId")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String id =exchange.getIn().getHeader("id", String.class);
                        String select= exchange.getIn().getBody(String.class);
                        Project project= gson.fromJson(select, Project.class);
                        projectRepository.updateProjectWithId(Integer.parseInt(id), project.getProjectName());

                    }
                });
        from("direct:projectDelete")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                    }
                });
        from("direct:projectDeleteId")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String id =exchange.getIn().getHeader("id", String.class);
                        projectRepository.deleteProject(Integer.parseInt(id));
                    }
                });


    }
}
