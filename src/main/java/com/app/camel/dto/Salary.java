package com.app.camel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@Builder
public class Salary {

    private Long id;
    private Long monthly;
    private Long perHour;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
