package ru.urusov.astonmvc.dao.impl;

import org.springframework.stereotype.Repository;
import ru.urusov.astonmvc.dao.EmployeesDao;
import ru.urusov.astonmvc.model.Employee;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeesDaoImpl implements EmployeesDao {

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
    public List<Employee> getAllEmployees() {
        final String QUERY = "SELECT * FROM employee;";

        List<Employee> employees = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            while(rs.next()){
                Employee employee = new Employee();
                employee.setId(rs.getLong("employee_id"));
                employee.setName(rs.getString("employee_name"));
                employee.setSalary(rs.getInt("employee_salary"));
                employee.setCity(rs.getString("employee_city"));
                employee.setBirthday(rs.getDate("employee_birthday"));

                System.out.println(employee);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee getById(Long id) {
        final String QUERY = "SELECT * FROM employee WHERE employee_id = (?);";
        Employee employee = new Employee();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(QUERY);
        ) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                employee.setId(resultSet.getLong("employee_id"));
                employee.setName(resultSet.getString("employee_name"));
                employee.setSalary(resultSet.getInt("employee_salary"));
                employee.setCity(resultSet.getString("employee_city"));
                employee.setBirthday(resultSet.getDate("employee_birthday"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void deleteById(Long id) {
        final String QUERY = "DELETE FROM employee WHERE employee_id = (?);";
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
    public void updateEmployeeById(Employee employee) {
        final String QUERY = "UPDATE employee SET employee_name = ?, employee_city = ?, employee_birthday = ? WHERE employee_id = (?);";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(QUERY);
        ) {
            stmt.setLong(4, employee.getId());
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getCity());
            stmt.setDate(3, new java.sql.Date( employee.getBirthday().getTime()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long saveEmployee(Employee employee) {
        final String QUERY = "INSERT INTO employee(employee_name, employee_city, employee_birthday) VALUES (?, ?, ?);";
        Long id = null;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(QUERY);
        ) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getCity());
            stmt.setDate(3, new java.sql.Date( employee.getBirthday().getTime()));
            id = (long) stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
