package com.app.camel.dao;

import com.app.camel.model.tables.records.UserRecord;

import java.sql.SQLException;

public interface UserRepository extends GenericRepository<Integer, UserRecord>{

    String getAllUserWithProject();

}
