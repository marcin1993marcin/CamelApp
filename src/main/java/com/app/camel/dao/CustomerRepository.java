package com.app.camel.dao;

import com.app.camel.model.tables.records.CustomerRecord;

import java.sql.SQLException;

public interface CustomerRepository extends GenericRepository<Integer, CustomerRecord>{

    String getAllCustomerWithProject() throws SQLException;

}
