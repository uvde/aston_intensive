package ru.urusov.astonmvc.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.urusov.astonmvc.dao.ProjectsDao;
import ru.urusov.astonmvc.model.Employee;
import ru.urusov.astonmvc.model.Project;
import ru.urusov.astonmvc.services.ProjectsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService {

    private final ProjectsDao projectDao;
    @Override
    public List<Project> getAllProjects() {
        return projectDao.getAllProjects();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectDao.getProjectById(id);
    }

    @Override
    public Long saveProject(Project project) {
        return projectDao.saveProject(project);
    }

    @Override
    public void deleteProjectById(Long id) {
        projectDao.deleteProjectById(id);
    }

    @Override
    public void updateProjectById(Project project) {
            projectDao.updateProjectById(project);
    }

    @Override
    public List<Employee> getEmployeesOnProject(long id) {
        return projectDao.getEmployeesOnProject(id);
    }

    @Override
    public void deleteEmployeeOnProjectById(long employeeId, long projectId) {
        projectDao.deleteEmployeeOnProjectById(employeeId, projectId);
    }
}
