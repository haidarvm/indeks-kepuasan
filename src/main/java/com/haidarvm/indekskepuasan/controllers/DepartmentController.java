package com.haidarvm.indekskepuasan.controllers;


import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    private static final String VIEWS_DEPARTMENT_CREATE_OR_UPDATE_FORM = "department/form";

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping({"", "index"})
    public String listDepartment(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "department/index";
    }

    @GetMapping("/{departmentId}")
    public String getDepartmentForm(@PathVariable Long departmentId, Model model) {
        model.addAttribute("department", departmentRepository.findById(departmentId));
        return VIEWS_DEPARTMENT_CREATE_OR_UPDATE_FORM;
    }
}
