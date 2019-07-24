package com.haidarvm.indekskepuasan.controllers;


import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportController {

//    private final DepartmentRepository departmentRepository;

    private final ScoreRepository scoreRepository;
    private final DepartmentRepository departmentRepository;

    public ReportController(ScoreRepository scoreRepository, DepartmentRepository departmentRepository) {
        this.scoreRepository = scoreRepository;
        this.departmentRepository = departmentRepository;
    }

//    public ReportController(DepartmentRepository departmentRepository) {
//        this.departmentRepository = departmentRepository;
//    }

    @RequestMapping({"", "/index"})
    public String generalReport(Model model){
        model.addAttribute("scores", scoreRepository.generalReport());
        model.addAttribute("totals", scoreRepository.countReport());
        return "report/index";
    }

    @RequestMapping("/{departmentId}")
    public String reportByDepartment(@PathVariable Long departmentId, Model model){
        model.addAttribute("scores", scoreRepository.generalReportByDepartment(departmentId));
        model.addAttribute("totals", scoreRepository.countReportByDepartment(departmentId));
        model.addAttribute("department", departmentRepository.findById(departmentId));
        return "report/department";
    }




}
