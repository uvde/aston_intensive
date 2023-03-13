package ru.urusov.astonmvc.services;

import ru.urusov.astonmvc.model.Employee;
import ru.urusov.astonmvc.model.Project;

import java.util.List;

public interface ProjectsService {
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Long saveProject(Project project);
    void deleteProjectById(Long id);
    void updateProjectById(Project project);
    List<Employee> getEmployeesOnProject(long id);
    void deleteEmployeeOnProjectById(long employeeId, long projectId);
}
