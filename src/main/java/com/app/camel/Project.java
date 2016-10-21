package com.app.camel;

public class Project {

    private final long id;
    private String projectName;

    public Project(long id, String projectName) {
        this.id = id;
        this.projectName = projectName;
    }

    public long getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
