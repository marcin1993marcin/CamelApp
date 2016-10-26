package com.app.camel.dao.impl;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.model.tables.records.ProjectRecord;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.tables.Project.PROJECT;

public class ProjectRepositoryImpl extends GenericRepository implements ProjectRepository {

    final static Logger logger = Logger.getLogger(ProjectRepositoryImpl.class);

    @Override
    public Optional<ProjectRecord> get(Integer id) {

        DSLContext dslContext = getDSLContext();

        Optional<ProjectRecord> project = Optional.empty();

        try {

            project = Optional.ofNullable(dslContext.selectFrom(PROJECT).where(PROJECT.ID.equal(id)).fetchOne());

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to access data");

        } finally {
            dslContext.close();
        }

        return project;
    }

    @Override
    public Collection<ProjectRecord> getAll() {

        Collection<ProjectRecord> projects = Lists.newArrayList();

        DSLContext dslContext = getDSLContext();

        try {
            Result<Record> result = dslContext.select().from(PROJECT).fetch();

            for (Record r : result) {
                ProjectRecord project = new ProjectRecord(r.getValue(PROJECT.ID), r.getValue(PROJECT.PROJECT_NAME));

                projects.add(project);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to access data");

        } finally {
            dslContext.close();
        }

        return projects;
    }


    @Override
    public boolean update(ProjectRecord project) {

        DSLContext dslContext = getDSLContext();

        try {
            dslContext.update(PROJECT)
                    .set(PROJECT.PROJECT_NAME, project.getProjectName())
                    .where(PROJECT.ID.eq(project.getId()))
                    .execute();

            return true;

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to update data");

        } finally {
            dslContext.close();
        }

        return false;
    }


    @Override
    public boolean insert(ProjectRecord project) {

        DSLContext dslContext = getDSLContext();

        com.app.camel.model.tables.Project p = com.app.camel.model.tables.Project.PROJECT;

        try {
            dslContext.insertInto(p)
                    .set(p.PROJECT_NAME, project.getProjectName())
                    .execute();

            return true;

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to insert data");

        } finally {
            dslContext.close();
        }

        return false;
    }


    @Override
    public boolean delete(Integer id) {

        DSLContext dslContext = getDSLContext();

        try {
            dslContext.delete(PROJECT).where(PROJECT.ID.eq(id)).execute();

            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to delete data");

        } finally {
            dslContext.close();
        }

        return false;
    }


    @Override
    public boolean deleteAll() {

        DSLContext dslContext = getDSLContext();

        try {
            Result<Record> result = dslContext.select().from(PROJECT).fetch();

            for (Record r : result) {

                dslContext.delete(PROJECT).where(PROJECT.ID.eq(r.getValue(PROJECT.ID))).execute();
            }

            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to delete data");

        } finally {
            dslContext.close();
        }

        return false;
    }
}
