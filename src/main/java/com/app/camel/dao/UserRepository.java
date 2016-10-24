package com.app.camel.dao;

import com.app.camel.model.tables.records.UserRecord;

public interface UserRepository extends GenericRepository<Integer, UserRecord>{

    String getAllUserWithProject();

}
