package com.haidarvm.indekskepuasan.controllers;


import com.haidarvm.indekskepuasan.model.Department;
import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    private static final String VIEWS_DEPARTMENT_CREATE_OR_UPDATE_FORM = "department/form";

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping({"", "index"})
    public String listDepartment(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        logger.debug("mana yaa Allah Ryzen 3900x  {}", departmentRepository.findById(1L).get().getTextService());
        return "department/index";
    }


    @GetMapping("/insert")
    public String insertDeaprtment(Model model) {
        model.addAttribute("department", Department.builder().build());
        return "department/form-insert";
    }

    @PostMapping("/insert")
    public String processInsertDepartment(@Valid Department department, BindingResult result) {
        if(result.hasErrors()) {
            return "department/form-insert";
        } else {
            Department savedDepartment = departmentRepository.save(department);
            return "redirect:/department";
        }
    }

    @GetMapping("update/{departmentId}")
    public String getDepartmentForm(@PathVariable Long departmentId, Model model) {
        model.addAttribute("department", departmentRepository.findById(departmentId));
        return "department/form-update";
    }

    @PostMapping("update/{departmentId}")
    public String processUpdateDepartmentForm(@Valid Department department, BindingResult result, @PathVariable Long departmentId) {
        if(result.hasErrors()) {
            return "department/form-update";
        } else {
            department.setId(departmentId);
            departmentRepository.save(department);
            return "redirect:/department";
        }
    }
}
