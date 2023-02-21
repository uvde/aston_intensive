package ru.urusov.astonmvc.services;

import ru.urusov.astonmvc.model.Employee;
import java.util.List;

public interface EmployeesService {

    List<Employee> getAllEmployees();
    Employee getById(Long id);
    Long saveEmployee(Employee employee);
    void deleteById(Long id);
    void updateEmployeeById(Employee employee);
}
