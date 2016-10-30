package com.app.camel.dao;

import com.app.camel.model.tables.records.UserRecord;

import java.sql.SQLException;

public interface UserRepository extends GenericRepository<Integer, UserRecord>{
    // review - brak javadoc
    // czy ta metoda jest gdziekolwiek używana?
    String getAllUserWithProject() throws SQLException;

}
