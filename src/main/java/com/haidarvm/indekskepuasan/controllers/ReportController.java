package com.haidarvm.indekskepuasan.controllers;


import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final DepartmentRepository departmentRepository;


    public ReportController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping({"", "/index"})
    public String listDepartment(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "department/index";
    }



}
