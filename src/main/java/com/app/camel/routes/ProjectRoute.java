package com.app.camel.routes;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.dao.impl.ProjectRepositoryImpl;
import com.app.camel.dto.Project;
import com.app.camel.model.tables.records.ProjectRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by britenet on 2016-10-21.
 */
public class ProjectRoute extends RouteBuilder {
    public static final String Url = "restlet:http://localhost:9091/project";

    @Override
    public void configure() throws Exception {


        final ProjectRepository projectRepository = new ProjectRepositoryImpl();
        final Gson gson = new GsonBuilder().create();

        from(Url + "?restletMethod=get").to("direct:projectSelect");
        from(Url + "/{id}?restletMethod=get").to("direct:projectSelectId");
        from(Url + "?restletMethod=post").to("direct:projectPost");
        from(Url + "?restletMethod=put").to("direct:projectPut");
        from(Url + "/{id}?restletMethod=put").to("direct:projectPutId");
        from(Url + "?restletMethod=delete").to("direct:projectDelete");
        from(Url + "/{id}?restletMethod=delete").to("direct:projectDeleteId");

       from("direct:projectSelect")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Collection<ProjectRecord> projects = projectRepository.getAll();
                        List<Project> projectlist = new ArrayList<Project>();
                        for(ProjectRecord projectRecord: projects)
                        {
                            Project project= new Project(projectRecord.getId(),projectRecord.getProjectName());
                            projectlist.add(project);
                        }
                        String json= gson.toJson(projectlist);
                        exchange.getIn().setBody(json);


                    }
                })
                .transform().body();
        from("direct:projectSelectId")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String id = exchange.getIn().getHeader("id", String.class);
                        ProjectRecord projectRecord = projectRepository.get(Integer.parseInt("id"));
                        Project project = new Project(projectRecord.getId(), projectRecord.getProjectName());
                        String json= gson.toJson(project);
                        exchange.getIn().setBody(json);
                    }
                })
                .transform().body();
        from("direct:projectPost")
                .process(new Processor() {
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
                }).transform().body();


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
                        String id = exchange.getIn().getHeader("id", String.class);
                        String select = exchange.getIn().getBody(String.class);
                        Project project = gson.fromJson(select, Project.class);
                        ProjectRecord projectRecord = new ProjectRecord();
                        projectRecord.setProjectName(project.getProjectName());
                        projectRecord.setId(Integer.parseInt("id"));
                        projectRepository.update(projectRecord);
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
                        String id = exchange.getIn().getHeader("id", String.class);
                        projectRepository.delete(Integer.parseInt("id"));

                        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
                        response.setStatus(Status.SUCCESS_NO_CONTENT);
                        exchange.getOut().setBody(response);
                    }
                }).transform().body();


    }
}
