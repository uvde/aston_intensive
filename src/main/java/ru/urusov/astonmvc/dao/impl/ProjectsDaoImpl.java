package ru.urusov.astonmvc.dao.impl;

import org.springframework.stereotype.Repository;
import ru.urusov.astonmvc.dao.ProjectsDao;
import ru.urusov.astonmvc.model.Employee;
import ru.urusov.astonmvc.model.Project;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectsDaoImpl implements ProjectsDao {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/aston";
    private static final String USER = "postgres";
    private static final String PASS = "prosto100";
    private static Driver driver;

    static {
        try {
            driver = (Driver) Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> getAllProjects() {
        final String QUERY = "SELECT * FROM project;";

        List<Project> projects = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            while(rs.next()){
                Project project = new Project();
                project.setId(rs.getLong("project_id"));
                project.setName(rs.getString("project_name"));

                System.out.println(project);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long id) {
        final String QUERY = "SELECT * FROM project WHERE project_id = (?);";
        Project project = new Project();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(QUERY);
        ) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                project.setId(resultSet.getLong("project_id"));
                project.setName(resultSet.getString("project_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public Long saveProject(Project project) {
        final String QUERY = "INSERT INTO project(project_name) VALUES (?);";
        Long id = null;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(QUERY);
        ) {
            stmt.setString(1, project.getName());
            id = (long) stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void deleteProjectById(Long id) {
        final String QUERY = "DELETE FROM project WHERE project_id = (?);";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(QUERY);
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProjectById(Project project) {
        final String QUERY = "UPDATE project SET project_name = ? WHERE project_id = (?);";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(QUERY);
        ) {
            stmt.setString(1, project.getName());
            stmt.setLong(2, project.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getEmployeesOnProject(long id) {
        final String QUERY = "SELECT *" +
                " FROM project p JOIN employee_project e_p" +
                " ON p.project_id = e_p.project_id" +
                " JOIN employee e" +
                " ON e_p.employee_id = e.employee_id" +
                " WHERE p.project_id = (?);";
        List<Employee> employees = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(QUERY);
        ) {
            stmt.setLong(1, id);
            ResultSet resultSet =  stmt.executeQuery();
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("employee_id"));
                employee.setName(resultSet.getString("employee_name"));
                employee.setSalary(resultSet.getInt("employee_salary"));
                employee.setCity(resultSet.getString("employee_city"));
                employee.setBirthday(resultSet.getDate("employee_birthday"));

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void deleteEmployeeOnProjectById(long employeeId, long projectId) {
        final String QUERY = "DELETE FROM employee_project WHERE project_id = (?) AND employee_id = (?);";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(QUERY);
        ) {
            stmt.setLong(1, projectId);
            stmt.setLong(2, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
