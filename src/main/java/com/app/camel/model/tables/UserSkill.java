/**
 * This class is generated by jOOQ
 */
package com.app.camel.model.tables;


import com.app.camel.model.Keys;
import com.app.camel.model.Library;
import com.app.camel.model.tables.records.UserSkillRecord;

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
public class UserSkill extends TableImpl<UserSkillRecord> {

    private static final long serialVersionUID = -416676816;

    /**
     * The reference instance of <code>library.user_skill</code>
     */
    public static final UserSkill USER_SKILL = new UserSkill();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserSkillRecord> getRecordType() {
        return UserSkillRecord.class;
    }

    /**
     * The column <code>library.user_skill.skill_id</code>.
     */
    public final TableField<UserSkillRecord, Integer> SKILL_ID = createField("skill_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>library.user_skill.user_id</code>.
     */
    public final TableField<UserSkillRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>library.user_skill.level</code>.
     */
    public final TableField<UserSkillRecord, Integer> LEVEL = createField("level", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>library.user_skill.note</code>.
     */
    public final TableField<UserSkillRecord, String> NOTE = createField("note", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>library.user_skill</code> table reference
     */
    public UserSkill() {
        this("user_skill", null);
    }

    /**
     * Create an aliased <code>library.user_skill</code> table reference
     */
    public UserSkill(String alias) {
        this(alias, USER_SKILL);
    }

    private UserSkill(String alias, Table<UserSkillRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserSkill(String alias, Table<UserSkillRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<UserSkillRecord> getPrimaryKey() {
        return Keys.KEY_USER_SKILL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserSkillRecord>> getKeys() {
        return Arrays.<UniqueKey<UserSkillRecord>>asList(Keys.KEY_USER_SKILL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<UserSkillRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserSkillRecord, ?>>asList(Keys.FK_SKILL_HAS_USER_SKILL1, Keys.FK_SKILL_HAS_USER_USER1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSkill as(String alias) {
        return new UserSkill(alias, this);
    }

    /**
     * Rename this table
     */
    public UserSkill rename(String name) {
        return new UserSkill(name, null);
    }
}
