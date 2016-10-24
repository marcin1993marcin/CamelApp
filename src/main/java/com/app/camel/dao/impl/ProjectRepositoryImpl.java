package com.app.camel.dao.impl;

import com.app.camel.configuration.DatabaseConfiguration;
import com.app.camel.dao.ProjectRepository;
import com.app.camel.model.tables.records.ProjectRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collection;

import static com.app.camel.model.tables.Project.PROJECT;

public class ProjectRepositoryImpl implements ProjectRepository {

    @Override
    public ProjectRecord get(Integer id) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            ProjectRecord project = dslContext.selectFrom(PROJECT).where(PROJECT.ID.equal(id)).fetchOne();

            return project;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Collection<ProjectRecord> getAll() {
        Collection<ProjectRecord> projects = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Result<Record> result = dslContext.select().from(PROJECT).fetch();

            for (Record r : result) {
                ProjectRecord project = new ProjectRecord(r.getValue(PROJECT.ID), r.getValue(PROJECT.PROJECT_NAME));

                projects.add(project);
            }

            return projects;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(ProjectRecord project) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.update(PROJECT)
                    .set(PROJECT.PROJECT_NAME, project)
                    .where(PROJECT.ID.eq(project.getId()))
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(ProjectRecord project) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            com.app.camel.model.tables.Project p = com.app.camel.model.tables.Project.PROJECT;

            dslContext.insertInto(p)
                    .set(p.PROJECT_NAME, project.getProjectName())
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.URL, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.delete(PROJECT).where(PROJECT.ID.eq(id)).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
