package com.app.camel.dao.impl;

import com.app.camel.configuration.Configuration;
import com.app.camel.dao.ProjectRepository;
import com.app.camel.model.tables.records.ProjectRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.USER;
import static com.app.camel.model.tables.Project.PROJECT;

public class ProjectRepositoryImpl implements ProjectRepository {

    @Override
    public Optional<ProjectRecord> get(Integer id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Optional<ProjectRecord> project = Optional.ofNullable(dslContext.selectFrom(PROJECT).where(PROJECT.ID.equal(id)).fetchOne());

            return project;

        }
    }

    @Override
    public Collection<ProjectRecord> getAll() throws SQLException {
        Collection<ProjectRecord> projects = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Result<Record> result = dslContext.select().from(PROJECT).fetch();

            for (Record r : result) {
                ProjectRecord project = new ProjectRecord(r.getValue(PROJECT.ID), r.getValue(PROJECT.PROJECT_NAME));

                projects.add(project);
            }

            return projects;

        }
    }

    @Override
    public boolean update(ProjectRecord project) {
        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.update(PROJECT)
                    .set(PROJECT.PROJECT_NAME, project.getProjectName())
                    .where(PROJECT.ID.eq(project.getId()))
                    .execute();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean insert(ProjectRecord project) {
        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            com.app.camel.model.tables.Project p = com.app.camel.model.tables.Project.PROJECT;

            dslContext.insertInto(p)
                    .set(p.PROJECT_NAME, project.getProjectName())
                    .execute();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.delete(PROJECT).where(PROJECT.ID.eq(id)).execute();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteAll() {
        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Result<Record> result = dslContext.select().from(PROJECT).fetch();

            for (Record r : result) {

                dslContext.delete(PROJECT).where(PROJECT.ID.eq(r.getValue(PROJECT.ID))).execute();
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
