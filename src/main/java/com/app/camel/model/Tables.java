/**
 * This class is generated by jOOQ
 */
package com.app.camel.model;


import com.app.camel.model.tables.Project;
import com.app.camel.model.tables.User;
import com.app.camel.model.tables.UserHasProject;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in library
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>library.project</code>.
     */
    public static final Project PROJECT = com.app.camel.model.tables.Project.PROJECT;

    /**
     * The table <code>library.user</code>.
     */
    public static final User USER = com.app.camel.model.tables.User.USER;

    /**
     * The table <code>library.user_has_project</code>.
     */
    public static final UserHasProject USER_HAS_PROJECT = com.app.camel.model.tables.UserHasProject.USER_HAS_PROJECT;
}