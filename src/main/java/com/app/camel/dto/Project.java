package com.app.camel.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Project {

    private final long id;
    private String projectName;
}
