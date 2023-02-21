package ru.urusov.astonmvc.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.urusov.astonmvc.model.Employee;
import ru.urusov.astonmvc.services.EmployeesService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/v1/employees")
public class EmployeesController {

    private final EmployeesService employeesService;

    @GetMapping()
    public String getAllEmployees(Model model){
        model.addAttribute("employees", employeesService.getAllEmployees());
        model.addAttribute("employee", new Employee());
        return "employees/all";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable("id") long id){
        model.addAttribute("employee", employeesService.getById(id));
        System.out.println(employeesService.getById(id));
        return "employees/employee";
    }

    @PostMapping()
    public String creatNewEmployee(@ModelAttribute("employee") Employee employee){
        employeesService.saveEmployee(employee);
        return "redirect:/v1/employees";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("employee", employeesService.getById(id));
        return "employees/edit";
    }

    @PatchMapping("/{id}")
    public String updateEmployeeById(@ModelAttribute("employee") Employee employee){
        employeesService.updateEmployeeById(employee);
        return "redirect:/v1/employees";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable("id") long id){
        employeesService.deleteById(id);
        return "redirect:/v1/employees";
    }
}
