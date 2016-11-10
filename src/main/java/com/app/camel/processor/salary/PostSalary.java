package com.app.camel.processor.salary;

import com.app.camel.dao.SalaryRepository;
import com.app.camel.dao.impl.SalaryRepositoryImpl;
import com.app.camel.dto.Salary;
import com.app.camel.model.tables.records.SalaryRecord;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

import java.sql.Date;

public class PostSalary implements Processor {

    private final SalaryRepository salaryRepository = new SalaryRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String select = exchange.getIn().getBody(String.class);

        Preconditions.checkNotNull(select, "Body is null");

        SalaryRecord salaryRecord = getSalaryRecord(gson.fromJson(select, Salary.class));

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_CREATED);

        if (!salaryRepository.insert(salaryRecord)) {
            response.setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
        }

        exchange.getOut().setBody(response);
    }

    private SalaryRecord getSalaryRecord(Salary salary) {
        SalaryRecord salaryRecord = new SalaryRecord();
        salaryRecord.setMonthly(Math.toIntExact(salary.getMonthly()));
        salaryRecord.setPerHour(Math.toIntExact(salary.getPerHour()));
        salaryRecord.setDateFrom(Date.valueOf(salary.getDateFrom()));
        salaryRecord.setDateTo(Date.valueOf(salary.getDateTo()));
        return salaryRecord;
    }
}