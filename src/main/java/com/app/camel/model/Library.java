/**
 * This class is generated by jOOQ
 */
package com.app.camel.model;


import com.app.camel.model.tables.Customer;
import com.app.camel.model.tables.CustomerProjects;
import com.app.camel.model.tables.Position;
import com.app.camel.model.tables.Project;
import com.app.camel.model.tables.Salary;
import com.app.camel.model.tables.SchemaVersion;
import com.app.camel.model.tables.Skill;
import com.app.camel.model.tables.User;
import com.app.camel.model.tables.UserProjects;
import com.app.camel.model.tables.UserSkill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Library extends SchemaImpl {

    private static final long serialVersionUID = 1601458231;

    /**
     * The reference instance of <code>library</code>
     */
    public static final Library LIBRARY = new Library();

    /**
     * The table <code>library.customer</code>.
     */
    public final Customer CUSTOMER = com.app.camel.model.tables.Customer.CUSTOMER;

    /**
     * The table <code>library.customer_projects</code>.
     */
    public final CustomerProjects CUSTOMER_PROJECTS = com.app.camel.model.tables.CustomerProjects.CUSTOMER_PROJECTS;

    /**
     * The table <code>library.position</code>.
     */
    public final Position POSITION = com.app.camel.model.tables.Position.POSITION;

    /**
     * The table <code>library.project</code>.
     */
    public final Project PROJECT = com.app.camel.model.tables.Project.PROJECT;

    /**
     * The table <code>library.salary</code>.
     */
    public final Salary SALARY = com.app.camel.model.tables.Salary.SALARY;

    /**
     * The table <code>library.schema_version</code>.
     */
    public final SchemaVersion SCHEMA_VERSION = com.app.camel.model.tables.SchemaVersion.SCHEMA_VERSION;

    /**
     * The table <code>library.skill</code>.
     */
    public final Skill SKILL = com.app.camel.model.tables.Skill.SKILL;

    /**
     * The table <code>library.user</code>.
     */
    public final User USER = com.app.camel.model.tables.User.USER;

    /**
     * The table <code>library.user_projects</code>.
     */
    public final UserProjects USER_PROJECTS = com.app.camel.model.tables.UserProjects.USER_PROJECTS;

    /**
     * The table <code>library.user_skill</code>.
     */
    public final UserSkill USER_SKILL = com.app.camel.model.tables.UserSkill.USER_SKILL;

    /**
     * No further instances allowed
     */
    private Library() {
        super("library", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Customer.CUSTOMER,
            CustomerProjects.CUSTOMER_PROJECTS,
            Position.POSITION,
            Project.PROJECT,
            Salary.SALARY,
            SchemaVersion.SCHEMA_VERSION,
            Skill.SKILL,
            User.USER,
            UserProjects.USER_PROJECTS,
            UserSkill.USER_SKILL);
    }
}
