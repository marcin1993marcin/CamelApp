/**
 * This class is generated by jOOQ
 */
package com.app.camel.model.tables;


import com.app.camel.model.Keys;
import com.app.camel.model.Library;
import com.app.camel.model.tables.records.SkillRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
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
public class Skill extends TableImpl<SkillRecord> {

    private static final long serialVersionUID = 574026666;

    /**
     * The reference instance of <code>library.skill</code>
     */
    public static final Skill SKILL = new Skill();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SkillRecord> getRecordType() {
        return SkillRecord.class;
    }

    /**
     * The column <code>library.skill.id</code>.
     */
    public final TableField<SkillRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>library.skill.name</code>.
     */
    public final TableField<SkillRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

    /**
     * The column <code>library.skill.parent_id</code>.
     */
    public final TableField<SkillRecord, Integer> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>library.skill</code> table reference
     */
    public Skill() {
        this("skill", null);
    }

    /**
     * Create an aliased <code>library.skill</code> table reference
     */
    public Skill(String alias) {
        this(alias, SKILL);
    }

    private Skill(String alias, Table<SkillRecord> aliased) {
        this(alias, aliased, null);
    }

    private Skill(String alias, Table<SkillRecord> aliased, Field<?>[] parameters) {
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
    public Identity<SkillRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SKILL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SkillRecord> getPrimaryKey() {
        return Keys.KEY_SKILL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SkillRecord>> getKeys() {
        return Arrays.<UniqueKey<SkillRecord>>asList(Keys.KEY_SKILL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<SkillRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<SkillRecord, ?>>asList(Keys.FK_SKILL_SKILL1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Skill as(String alias) {
        return new Skill(alias, this);
    }

    /**
     * Rename this table
     */
    public Skill rename(String name) {
        return new Skill(name, null);
    }
}
