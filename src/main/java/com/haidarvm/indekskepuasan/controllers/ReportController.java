package com.haidarvm.indekskepuasan.controllers;


import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/report")
public  class ReportController {


    @Value("${welcome.message}")
    private String message;


    private final ScoreRepository scoreRepository;
    private final DepartmentRepository departmentRepository;

    public ReportController(ScoreRepository scoreRepository, DepartmentRepository departmentRepository) {
        this.scoreRepository = scoreRepository;
        this.departmentRepository = departmentRepository;
    }


    @RequestMapping("")
    public String generalReport(Model model){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(LocalDate.now() + " 00:00:00", formatter);
        LocalDateTime endDate = LocalDateTime.parse(LocalDate.now() + " 23:59:59", formatter);
        model.addAttribute("scores", scoreRepository.generalReport());
        model.addAttribute("scoreSatisfy", scoreRepository.countByScoreAndCreatedBetween(1,startDate, endDate));
        model.addAttribute("scoreDissatisfy", scoreRepository.countByScoreAndCreatedBetween(-1,startDate, endDate));
        model.addAttribute("countAll", scoreRepository.countAllByCreatedBetween(startDate, endDate));
        return "report/index";
    }

    @RequestMapping("/{departmentId}")
    public String reportByDepartment(@PathVariable Long departmentId, Model model){
        model.addAttribute("scores", scoreRepository.generalReportByDepartment(departmentId));
        model.addAttribute("totals", scoreRepository.countReportByDepartment(departmentId));
        model.addAttribute("department", departmentRepository.findById(departmentId));
        return "report/department";
    }

    @RequestMapping("detail/{departmentId}")
    public String reportDetailByDepartment(@PathVariable Long departmentId, Model model){
        model.addAttribute("scores", scoreRepository.generalReportByDepartment(departmentId));
        model.addAttribute("totals", scoreRepository.countReportByDepartment(departmentId));
        model.addAttribute("department", departmentRepository.findById(departmentId));
        return "report/department";
    }



}
