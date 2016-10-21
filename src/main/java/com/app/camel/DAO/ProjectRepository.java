package com.app.camel.DAO;

public interface ProjectRepository {

    public String getAllProjects();

    public String getProjectById(Integer id);

    public void addProject(String projectName);

    public void deleteProject(Integer id);

    public void updateProjectWithId(Integer id, String projectName);
}
