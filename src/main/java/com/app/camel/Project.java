package com.app.camel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Project {

    private final long id;
    private String projectName;

    public Project(long id, String projectName) {
        this.id = id;
        this.projectName = projectName;
    }
}
