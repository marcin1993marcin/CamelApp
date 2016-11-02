package com.app.camel.dao.impl;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.model.tables.Project;
import com.app.camel.model.tables.records.ProjectRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.tables.Project.PROJECT;

public class ProjectRepositoryImpl extends GenericRepository implements ProjectRepository {

    private static final Logger LOGGER = Logger.getLogger(ProjectRepositoryImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProjectRecord> get(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Getting project failed! Id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Getting project with id: " + id);

        Optional<ProjectRecord> projectRecord = executeQuery(ctx -> Optional.ofNullable(
                ctx.selectFrom(PROJECT)
                        .where(PROJECT.ID.equal(id))
                        .fetchOne()));

        if (projectRecord.isPresent()) {
            LOGGER.info("Project with id: " + id + " fetched successfully");
        } else {
            LOGGER.info("Project with id: " + id + " not found");
        }

        return projectRecord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<ProjectRecord> getAll() {

        LOGGER.info("Getting all projects from database");

        return executeQuery(ctx -> {

            Result<Record> result = ctx.select().from(PROJECT).fetch();

            Collection<ProjectRecord> projects = Lists.newArrayList();

            result.forEach(r -> {
                ProjectRecord project = new ProjectRecord(
                        r.getValue(PROJECT.ID),
                        r.getValue(PROJECT.PROJECT_NAME));

                projects.add(project);
            });

            if (projects.size() > 0) {
                LOGGER.info("Successfully fetched all projects from database");
            } else {
                LOGGER.info("Cannot get all projects. No projects found in database");
            }

            return projects;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(ProjectRecord project) {

        try {
            Preconditions.checkNotNull(project);
            Preconditions.checkNotNull(project.getId());
        } catch (NullPointerException ex) {
            LOGGER.error("Updating project record failed! ProjectRecord or ProjectRecord id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Updating project with id: " + project.getId());

        return executeQuery(ctx -> {
            int count = ctx.update(PROJECT)
                    .set(PROJECT.PROJECT_NAME, project.getProjectName())
                    .where(PROJECT.ID.eq(project.getId()))
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully updated project with id: " + project.getId());
            } else {
                LOGGER.info("Cannot update project with id: " + project.getId() + ". Project not found");
            }

            return count > 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(ProjectRecord project) {

        try {
            Preconditions.checkNotNull(project);
        } catch (NullPointerException ex) {
            LOGGER.error("Adding project record failed! ProjectRecord cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Adding project with id: " + project.getId());

        return executeQuery(ctx -> {
            Project p = Project.PROJECT;

            int count = ctx.insertInto(p)
                    .set(p.PROJECT_NAME, project.getProjectName())
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully added project with id: " + project.getId());
            } else {
                LOGGER.info("Cannot add project with id: " + project.getId());
            }

            return count > 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Deleting project failed! Id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Deleting project with id: " + id);

        return executeQuery(ctx -> {
            int count = ctx.delete(PROJECT).where(PROJECT.ID.eq(id)).execute();

            if (count > 0) {
                LOGGER.info("Project with id: " + id + " deleted successfully");
            } else {
                LOGGER.info("Project not found! Cannot delete project with id: " + id);
            }

            return count > 0;
        });
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAll() {

        LOGGER.info("Deleting all projects");

        return executeQuery(ctx -> {

            int count = ctx.delete(PROJECT).execute();

            if (count > 0) {
                LOGGER.info("All projects deleted successfully");
            } else {
                LOGGER.info("Cannot delete projects. Database is already empty");
            }

            return count > 0;
        });
    }
}
