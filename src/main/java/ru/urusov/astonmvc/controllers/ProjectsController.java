package ru.urusov.astonmvc.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.urusov.astonmvc.model.Employee;
import ru.urusov.astonmvc.model.Project;
import ru.urusov.astonmvc.services.ProjectsService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/v1/projects")
public class ProjectsController {

        private final ProjectsService projectsService;

    @GetMapping()
    public String getAllProjects(Model model){
        model.addAttribute("projects", projectsService.getAllProjects());
        model.addAttribute("project", new Project());
        return "projects/all";
    }

    @PostMapping()
    public String creatNewProject(@ModelAttribute("project") Project project){
        projectsService.saveProject(project);
        return "redirect:/v1/projects";
    }

    @DeleteMapping("/{id}")
    public String deleteProjectById(@PathVariable("id") long id){
        projectsService.deleteProjectById(id);
        return "redirect:/v1/projects";
    }
    @DeleteMapping("/employee/{employeeId}/{projectId}")
    public String deleteEmployeeOnProjectById(@PathVariable("employeeId") long employeeId, @PathVariable("projectId") long projectId){
        projectsService.deleteEmployeeOnProjectById(employeeId, projectId);
        return "redirect:/v1/projects/" + projectId;
    }


    @PatchMapping("/{id}")
    public String updateEmployeeById(@ModelAttribute("project") Project project){
        projectsService.updateProjectById(project);
        return "redirect:/v1/projects";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable("id") long id){
        model.addAttribute("project", projectsService.getProjectById(id));
        model.addAttribute("employees", projectsService.getEmployeesOnProject(id));
        return "projects/project";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("project", projectsService.getProjectById(id));
        return "projects/edit";
    }

}
