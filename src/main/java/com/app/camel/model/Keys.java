/**
 * This class is generated by jOOQ
 */
package com.app.camel.model;


import com.app.camel.model.tables.Position;
import com.app.camel.model.tables.Customer;
import com.app.camel.model.tables.CustomerProjects;
import com.app.camel.model.tables.Project;
import com.app.camel.model.tables.Salary;
import com.app.camel.model.tables.Skill;
import com.app.camel.model.tables.User;
import com.app.camel.model.tables.UserProjects;
import com.app.camel.model.tables.UserSkill;
import com.app.camel.model.tables.records.PositionRecord;
import com.app.camel.model.tables.records.CustomerProjectsRecord;
import com.app.camel.model.tables.records.CustomerRecord;
import com.app.camel.model.tables.records.ProjectRecord;
import com.app.camel.model.tables.records.SalaryRecord;
import com.app.camel.model.tables.records.SkillRecord;
import com.app.camel.model.tables.records.UserProjectsRecord;
import com.app.camel.model.tables.records.UserRecord;
import com.app.camel.model.tables.records.UserSkillRecord;

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

    public static final Identity<PositionRecord, Integer> IDENTITY_POSITION = Identities0.IDENTITY_POSITION;
    public static final Identity<CustomerRecord, Integer> IDENTITY_CUSTOMER = Identities0.IDENTITY_CUSTOMER;
    public static final Identity<ProjectRecord, Integer> IDENTITY_PROJECT = Identities0.IDENTITY_PROJECT;
    public static final Identity<SalaryRecord, Integer> IDENTITY_SALARY = Identities0.IDENTITY_SALARY;
    public static final Identity<SkillRecord, Integer> IDENTITY_SKILL = Identities0.IDENTITY_SKILL;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<PositionRecord> KEY_POSITION_PRIMARY = UniqueKeys0.KEY_POSITION_PRIMARY;
    public static final UniqueKey<CustomerRecord> KEY_CUSTOMER_PRIMARY = UniqueKeys0.KEY_CUSTOMER_PRIMARY;
    public static final UniqueKey<CustomerProjectsRecord> KEY_CUSTOMER_PROJECTS_PRIMARY = UniqueKeys0.KEY_CUSTOMER_PROJECTS_PRIMARY;
    public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = UniqueKeys0.KEY_PROJECT_PRIMARY;
    public static final UniqueKey<SalaryRecord> KEY_SALARY_PRIMARY = UniqueKeys0.KEY_SALARY_PRIMARY;
    public static final UniqueKey<SkillRecord> KEY_SKILL_PRIMARY = UniqueKeys0.KEY_SKILL_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserProjectsRecord> KEY_USER_PROJECTS_PRIMARY = UniqueKeys0.KEY_USER_PROJECTS_PRIMARY;
    public static final UniqueKey<UserSkillRecord> KEY_USER_SKILL_PRIMARY = UniqueKeys0.KEY_USER_SKILL_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<SalaryRecord, UserRecord> FK_USERS_HAS_SALARIES_POSITIONS = ForeignKeys0.FK_USERS_HAS_SALARIES_POSITIONS;
    public static final ForeignKey<SalaryRecord, PositionRecord> FK_POSITION_HAS_SALARIES_POSITIONS = ForeignKeys0.FK_POSITION_HAS_SALARIES_POSITIONS;
    public static final ForeignKey<SkillRecord, SkillRecord> FK_SKILL_SKILL1 = ForeignKeys0.FK_SKILL_SKILL1;
    public static final ForeignKey<UserRecord, PositionRecord> FK_POSITION_HAS_USERS_POSITIONS = ForeignKeys0.FK_POSITION_HAS_USERS_POSITIONS;
    public static final ForeignKey<CustomerProjectsRecord, CustomerRecord> FK_CUSTOMER_PROJECTS_CUSTOMER = ForeignKeys0.FK_CUSTOMER_PROJECTS_CUSTOMER;
    public static final ForeignKey<CustomerProjectsRecord, ProjectRecord> FK_CUSTOMER_PROJECTS_PROJECT = ForeignKeys0.FK_CUSTOMER_PROJECTS_PROJECT;
    public static final ForeignKey<UserProjectsRecord, UserRecord> FK_USERS_HAS_PROJECTS_USERS = ForeignKeys0.FK_USERS_HAS_PROJECTS_USERS;
    public static final ForeignKey<UserProjectsRecord, ProjectRecord> FK_USERS_HAS_PROJECTS_PROJECTS1 = ForeignKeys0.FK_USERS_HAS_PROJECTS_PROJECTS1;
    public static final ForeignKey<UserSkillRecord, SkillRecord> FK_SKILL_HAS_USER_SKILL1 = ForeignKeys0.FK_SKILL_HAS_USER_SKILL1;
    public static final ForeignKey<UserSkillRecord, UserRecord> FK_SKILL_HAS_USER_USER1 = ForeignKeys0.FK_SKILL_HAS_USER_USER1;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<PositionRecord, Integer> IDENTITY_POSITION = createIdentity(Position.POSITION, Position.POSITION.ID);
        public static Identity<CustomerRecord, Integer> IDENTITY_CUSTOMER = createIdentity(Customer.CUSTOMER, Customer.CUSTOMER.ID);
        public static Identity<ProjectRecord, Integer> IDENTITY_PROJECT = createIdentity(Project.PROJECT, Project.PROJECT.ID);
        public static Identity<SalaryRecord, Integer> IDENTITY_SALARY = createIdentity(Salary.SALARY, Salary.SALARY.ID);
        public static Identity<SkillRecord, Integer> IDENTITY_SKILL = createIdentity(Skill.SKILL, Skill.SKILL.ID);
        public static Identity<UserRecord, Integer> IDENTITY_USER = createIdentity(User.USER, User.USER.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<PositionRecord> KEY_POSITION_PRIMARY = createUniqueKey(Position.POSITION, "KEY_position_PRIMARY", Position.POSITION.ID);
        public static final UniqueKey<CustomerRecord> KEY_CUSTOMER_PRIMARY = createUniqueKey(Customer.CUSTOMER, "KEY_customer_PRIMARY", Customer.CUSTOMER.ID);
        public static final UniqueKey<CustomerProjectsRecord> KEY_CUSTOMER_PROJECTS_PRIMARY = createUniqueKey(CustomerProjects.CUSTOMER_PROJECTS, "KEY_customer_projects_PRIMARY", CustomerProjects.CUSTOMER_PROJECTS.CUSTOMER_ID, CustomerProjects.CUSTOMER_PROJECTS.PROJECT_ID);
        public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = createUniqueKey(Project.PROJECT, "KEY_project_PRIMARY", Project.PROJECT.ID);
        public static final UniqueKey<SalaryRecord> KEY_SALARY_PRIMARY = createUniqueKey(Salary.SALARY, "KEY_salary_PRIMARY", Salary.SALARY.ID);
        public static final UniqueKey<SkillRecord> KEY_SKILL_PRIMARY = createUniqueKey(Skill.SKILL, "KEY_skill_PRIMARY", Skill.SKILL.ID);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = createUniqueKey(User.USER, "KEY_user_PRIMARY", User.USER.ID);
        public static final UniqueKey<UserProjectsRecord> KEY_USER_PROJECTS_PRIMARY = createUniqueKey(UserProjects.USER_PROJECTS, "KEY_user_projects_PRIMARY", UserProjects.USER_PROJECTS.USERS_ID, UserProjects.USER_PROJECTS.PROJECTS_ID);
        public static final UniqueKey<UserSkillRecord> KEY_USER_SKILL_PRIMARY = createUniqueKey(UserSkill.USER_SKILL, "KEY_user_skill_PRIMARY", UserSkill.USER_SKILL.SKILL_ID, UserSkill.USER_SKILL.USER_ID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<SalaryRecord, UserRecord> FK_USERS_HAS_SALARIES_POSITIONS = createForeignKey(com.app.camel.model.Keys.KEY_USER_PRIMARY, Salary.SALARY, "fk_Users_has_Salaries_Positions", Salary.SALARY.USERS_ID);
        public static final ForeignKey<SalaryRecord, PositionRecord> FK_POSITION_HAS_SALARIES_POSITIONS = createForeignKey(com.app.camel.model.Keys.KEY_POSITION_PRIMARY, Salary.SALARY, "fk_Position_has_Salaries_Positions", Salary.SALARY.POSITIONS_ID);
        public static final ForeignKey<SkillRecord, SkillRecord> FK_SKILL_SKILL1 = createForeignKey(com.app.camel.model.Keys.KEY_SKILL_PRIMARY, Skill.SKILL, "fk_skill_skill1", Skill.SKILL.PARENT_ID);
        public static final ForeignKey<UserRecord, PositionRecord> FK_POSITION_HAS_USERS_POSITIONS = createForeignKey(com.app.camel.model.Keys.KEY_POSITION_PRIMARY, User.USER, "fk_Position_has_Users_Positions", User.USER.POSITION_ID);
        public static final ForeignKey<CustomerProjectsRecord, CustomerRecord> FK_CUSTOMER_PROJECTS_CUSTOMER = createForeignKey(com.app.camel.model.Keys.KEY_CUSTOMER_PRIMARY, CustomerProjects.CUSTOMER_PROJECTS, "fk_customer_projects_customer", CustomerProjects.CUSTOMER_PROJECTS.CUSTOMER_ID);
        public static final ForeignKey<CustomerProjectsRecord, ProjectRecord> FK_CUSTOMER_PROJECTS_PROJECT = createForeignKey(com.app.camel.model.Keys.KEY_PROJECT_PRIMARY, CustomerProjects.CUSTOMER_PROJECTS, "fk_customer_projects_project", CustomerProjects.CUSTOMER_PROJECTS.PROJECT_ID);
        public static final ForeignKey<UserProjectsRecord, UserRecord> FK_USERS_HAS_PROJECTS_USERS = createForeignKey(com.app.camel.model.Keys.KEY_USER_PRIMARY, UserProjects.USER_PROJECTS, "fk_Users_has_Projects_Users", UserProjects.USER_PROJECTS.USERS_ID);
        public static final ForeignKey<UserProjectsRecord, ProjectRecord> FK_USERS_HAS_PROJECTS_PROJECTS1 = createForeignKey(com.app.camel.model.Keys.KEY_PROJECT_PRIMARY, UserProjects.USER_PROJECTS, "fk_Users_has_Projects_Projects1", UserProjects.USER_PROJECTS.PROJECTS_ID);
        public static final ForeignKey<UserSkillRecord, SkillRecord> FK_SKILL_HAS_USER_SKILL1 = createForeignKey(com.app.camel.model.Keys.KEY_SKILL_PRIMARY, UserSkill.USER_SKILL, "fk_skill_has_user_skill1", UserSkill.USER_SKILL.SKILL_ID);
        public static final ForeignKey<UserSkillRecord, UserRecord> FK_SKILL_HAS_USER_USER1 = createForeignKey(com.app.camel.model.Keys.KEY_USER_PRIMARY, UserSkill.USER_SKILL, "fk_skill_has_user_user1", UserSkill.USER_SKILL.USER_ID);
    }
}
