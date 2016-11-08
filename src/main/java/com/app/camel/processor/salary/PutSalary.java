package com.app.camel.processor.salary;

import com.app.camel.dao.SalaryRepository;
import com.app.camel.dao.impl.SalaryRepositoryImpl;
import com.app.camel.dto.Salary;
import com.app.camel.model.tables.records.SalaryRecord;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

import java.sql.Date;

public class PutSalary implements Processor {

    private final SalaryRepository salaryRepository = new SalaryRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);

        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid salary ID of value: \"" + id + "\"");

        String select = exchange.getIn().getBody(String.class);

        Preconditions.checkNotNull(select, "Body is null");

        Salary salary = gson.fromJson(select, Salary.class);

        SalaryRecord salaryRecord = new SalaryRecord();
        salaryRecord.setId(Integer.parseInt(id));
        salaryRecord.setMonthly(Math.toIntExact(salary.getMonthly()));
        salaryRecord.setPerHour(Math.toIntExact(salary.getPerHour()));
        salaryRecord.setDateFrom(Date.valueOf(salary.getDateFrom()));
        salaryRecord.setDateTo(Date.valueOf(salary.getDateTo()));

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_CREATED);

        if (!salaryRepository.update(salaryRecord)) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);
    }
}
