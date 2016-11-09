package com.app.camel.processor.salary;

import com.app.camel.dao.SalaryRepository;
import com.app.camel.dao.impl.SalaryRepositoryImpl;
import com.app.camel.dto.Salary;
import com.app.camel.model.tables.records.SalaryRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SelectAllSalary implements Processor {

    private final SalaryRepository salaryRepository = new SalaryRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        Collection<SalaryRecord> salaries = salaryRepository.getAll();

        List<Salary> salaryList = salaries.stream().map(salaryEntity -> Salary.builder()
            .id(Long.valueOf(salaryEntity.getId()))
            .monthly(Long.valueOf(salaryEntity.getMonthly()))
            .perHour(Long.valueOf(salaryEntity.getPerHour()))
            .dateFrom(salaryEntity.getDateFrom().toLocalDate())
            .dateTo(salaryEntity.getDateTo().toLocalDate())
            .build()
        ).collect(toList());

        exchange.getIn().setBody(gson.toJson(salaryList));
    }
}
