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

import java.util.Optional;

import static java.lang.Long.valueOf;

public class SelectSalary implements Processor {

    private final SalaryRepository salaryRepository = new SalaryRepositoryImpl();
    private Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid salary ID of value: \"" + id + "\"");

        Optional<SalaryRecord> salaryRecord = salaryRepository.get(Integer.parseInt(id));

        if (salaryRecord.isPresent()) {
            Salary salary = Salary.builder()
                    .id(valueOf(salaryRecord.get().getId()))
                    .monthly(salaryRecord.get().getMonthly().longValue())
                    .perHour(salaryRecord.get().getPerHour().longValue())
                    .dateFrom(salaryRecord.get().getDateFrom().toLocalDate())
                    .dateTo(salaryRecord.get().getDateTo().toLocalDate())
                    .build();

            exchange.getIn().setBody(gson.toJson(salary));

        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_NO_CONTENT);
            exchange.getOut().setBody(response);
        }
    }
}
