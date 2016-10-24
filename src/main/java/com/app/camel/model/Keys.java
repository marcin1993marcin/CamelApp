/**
 * This class is generated by jOOQ
 */
package com.app.camel.model;


import com.app.camel.model.tables.Project;
import com.app.camel.model.tables.User;
import com.app.camel.model.tables.UserProjects;
import com.app.camel.model.tables.records.ProjectRecord;
import com.app.camel.model.tables.records.UserProjectsRecord;
import com.app.camel.model.tables.records.UserRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>library</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ProjectRecord, Integer> IDENTITY_PROJECT = Identities0.IDENTITY_PROJECT;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = UniqueKeys0.KEY_PROJECT_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserProjectsRecord> KEY_USER_PROJECTS_PRIMARY = UniqueKeys0.KEY_USER_PROJECTS_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<UserProjectsRecord, UserRecord> FK_USERS_HAS_PROJECTS_USERS = ForeignKeys0.FK_USERS_HAS_PROJECTS_USERS;
    public static final ForeignKey<UserProjectsRecord, ProjectRecord> FK_USERS_HAS_PROJECTS_PROJECTS1 = ForeignKeys0.FK_USERS_HAS_PROJECTS_PROJECTS1;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<ProjectRecord, Integer> IDENTITY_PROJECT = createIdentity(Project.PROJECT, Project.PROJECT.ID);
        public static Identity<UserRecord, Integer> IDENTITY_USER = createIdentity(User.USER, User.USER.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = createUniqueKey(Project.PROJECT, "KEY_project_PRIMARY", Project.PROJECT.ID);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = createUniqueKey(User.USER, "KEY_user_PRIMARY", User.USER.ID);
        public static final UniqueKey<UserProjectsRecord> KEY_USER_PROJECTS_PRIMARY = createUniqueKey(UserProjects.USER_PROJECTS, "KEY_user_projects_PRIMARY", UserProjects.USER_PROJECTS.USERS_ID, UserProjects.USER_PROJECTS.PROJECTS_ID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<UserProjectsRecord, UserRecord> FK_USERS_HAS_PROJECTS_USERS = createForeignKey(com.app.camel.model.Keys.KEY_USER_PRIMARY, UserProjects.USER_PROJECTS, "fk_Users_has_Projects_Users", UserProjects.USER_PROJECTS.USERS_ID);
        public static final ForeignKey<UserProjectsRecord, ProjectRecord> FK_USERS_HAS_PROJECTS_PROJECTS1 = createForeignKey(com.app.camel.model.Keys.KEY_PROJECT_PRIMARY, UserProjects.USER_PROJECTS, "fk_Users_has_Projects_Projects1", UserProjects.USER_PROJECTS.PROJECTS_ID);
    }
}
