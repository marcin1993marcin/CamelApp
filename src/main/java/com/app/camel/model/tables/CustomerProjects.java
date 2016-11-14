/**
 * This class is generated by jOOQ
 */
package com.app.camel.model.tables;


import com.app.camel.model.Keys;
import com.app.camel.model.Library;
import com.app.camel.model.tables.records.CustomerProjectsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class CustomerProjects extends TableImpl<CustomerProjectsRecord> {

    private static final long serialVersionUID = 791652718;

    /**
     * The reference instance of <code>library.customer_projects</code>
     */
    public static final CustomerProjects CUSTOMER_PROJECTS = new CustomerProjects();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CustomerProjectsRecord> getRecordType() {
        return CustomerProjectsRecord.class;
    }

    /**
     * The column <code>library.customer_projects.Customer_id</code>.
     */
    public final TableField<CustomerProjectsRecord, Integer> CUSTOMER_ID = createField("Customer_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>library.customer_projects.Project_id</code>.
     */
    public final TableField<CustomerProjectsRecord, Integer> PROJECT_ID = createField("Project_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>library.customer_projects</code> table reference
     */
    public CustomerProjects() {
        this("customer_projects", null);
    }

    /**
     * Create an aliased <code>library.customer_projects</code> table reference
     */
    public CustomerProjects(String alias) {
        this(alias, CUSTOMER_PROJECTS);
    }

    private CustomerProjects(String alias, Table<CustomerProjectsRecord> aliased) {
        this(alias, aliased, null);
    }

    private CustomerProjects(String alias, Table<CustomerProjectsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Library.LIBRARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CustomerProjectsRecord> getPrimaryKey() {
        return Keys.KEY_CUSTOMER_PROJECTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CustomerProjectsRecord>> getKeys() {
        return Arrays.<UniqueKey<CustomerProjectsRecord>>asList(Keys.KEY_CUSTOMER_PROJECTS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CustomerProjectsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CustomerProjectsRecord, ?>>asList(Keys.FK_CUSTOMER_PROJECTS_CUSTOMER, Keys.FK_CUSTOMER_PROJECTS_PROJECT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerProjects as(String alias) {
        return new CustomerProjects(alias, this);
    }

    /**
     * Rename this table
     */
    public CustomerProjects rename(String name) {
        return new CustomerProjects(name, null);
    }
}
