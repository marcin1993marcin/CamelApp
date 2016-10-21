package com.app.camel.DAO;

import com.app.camel.Config;
import com.app.camel.Project;
import com.app.camel.model.tables.records.ProjectRecord;
import com.google.gson.Gson;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static com.app.camel.model.tables.Project.PROJECT;

public class ProjectRepositoryImpl implements ProjectRepository {


    @Override
    public String getAllProjects() {
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Result<Record> result = dslContext.select().from(PROJECT).fetch();

            for (Record r : result) {
                Project project = new Project(r.getValue(PROJECT.ID), r.getValue(PROJECT.PROJECT_NAME));

                projects.add(project);
            }

            return new Gson().toJson(projects);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getProjectById(Integer id) {
        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            ProjectRecord userRecords = dslContext.selectFrom(PROJECT).where(PROJECT.ID.equal(id)).fetchOne();

            Project project = new Project(
                    userRecords.getValue(PROJECT.ID),
                    userRecords.getValue(PROJECT.PROJECT_NAME));

            return new Gson().toJson(project);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addProject(String projectName) {

        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            com.app.camel.model.tables.Project project = com.app.camel.model.tables.Project.PROJECT;

            dslContext.insertInto(project)
                    .set(project.PROJECT_NAME, projectName)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProject(Integer id) {

        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.delete(PROJECT).where(PROJECT.ID.eq(id)).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProjectWithId(Integer id, String projectName) {

        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.update(PROJECT)
                    .set(PROJECT.PROJECT_NAME, projectName)
                    .where(PROJECT.ID.eq(id))
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
