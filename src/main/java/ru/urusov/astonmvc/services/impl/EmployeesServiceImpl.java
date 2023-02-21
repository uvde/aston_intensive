package ru.urusov.astonmvc.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.urusov.astonmvc.dao.EmployeesDao;
import ru.urusov.astonmvc.model.Employee;
import ru.urusov.astonmvc.services.EmployeesService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService {

    private final EmployeesDao employeesDto;

    @Override
    public List<Employee> getAllEmployees() {
        return employeesDto.getAllEmployees();
    }

    @Override
    public Employee getById(Long id) {
        return employeesDto.getById(id);
    }

    @Override
    public Long saveEmployee(Employee employee) {
        return employeesDto.saveEmployee(employee);
    }

    @Override
    public void deleteById(Long id) {
        employeesDto.deleteById(id);
    }

    @Override
    public void updateEmployeeById(Employee employee) {
        employeesDto.updateEmployeeById(employee);
    }
}
