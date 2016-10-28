package com.app.camel.dao.impl;

import com.app.camel.dao.ProjectRepository;
import com.app.camel.model.tables.records.ProjectRecord;
import com.google.common.collect.Lists;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.tables.Project.PROJECT;

public class ProjectRepositoryImpl extends GenericRepository implements ProjectRepository {

    @Override
    public Optional<ProjectRecord> get(Integer id) {

        return executeQuery(ctx -> Optional.ofNullable(
                ctx.selectFrom(PROJECT)
                        .where(PROJECT.ID.equal(id))
                        .fetchOne()));
    }

    @Override
    public Collection<ProjectRecord> getAll() {

        return executeQuery(ctx -> {

            Result<Record> result = ctx.select().from(PROJECT).fetch();

            Collection<ProjectRecord> projects = Lists.newArrayList();

            for (Record r : result) {
                ProjectRecord project = new ProjectRecord(
                        r.getValue(PROJECT.ID),
                        r.getValue(PROJECT.PROJECT_NAME));

                projects.add(project);

            }

            return projects;
        });
    }


    @Override
    public boolean update(ProjectRecord project) {

        return executeQuery(ctx -> {
            int count = ctx.update(PROJECT)
                    .set(PROJECT.PROJECT_NAME, project.getProjectName())
                    .where(PROJECT.ID.eq(project.getId()))
                    .execute();

            return count > 0;
        });
    }


    @Override
    public boolean insert(ProjectRecord project) {

        return executeQuery(ctx -> {
            com.app.camel.model.tables.Project p = com.app.camel.model.tables.Project.PROJECT;

            int count = ctx.insertInto(p)
                    .set(p.PROJECT_NAME, project.getProjectName())
                    .execute();

            return count > 0;
        });
    }


    @Override
    public boolean delete(Integer id) {

        return executeQuery(ctx -> {
            int count = ctx.delete(PROJECT).where(PROJECT.ID.eq(id)).execute();

            return count > 0;
        });
    }


    @Override
    public boolean deleteAll() {

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(PROJECT).fetch();

            int count = 0;
            for (Record r : result) {

                count = ctx.delete(PROJECT).where(PROJECT.ID.eq(r.getValue(PROJECT.ID))).execute();
            }

            return count > 0;
        });
    }
}
