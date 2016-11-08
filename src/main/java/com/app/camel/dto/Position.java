package com.app.camel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Position {

    private Long id;
    private String position;
}
