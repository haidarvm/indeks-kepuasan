package com.haidarvm.indekskepuasan.controllers;


import com.google.gson.Gson;
import com.haidarvm.indekskepuasan.model.Score;
import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/report")
public  class ReportController {


    @Value("${welcome.message}")
    private String message;


    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
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
        model.addAttribute("score", new Score());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("scores", scoreRepository.generalReport());
        return "report/index";
    }

    @RequestMapping("/report_by/{departmentId}/{startDate}/{endDate}")
    public String reportBy(@PathVariable Long departmentId, @PathVariable String startDate, @PathVariable String endDate, Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        if(departmentId == 0L) {
            model.addAttribute("scores", scoreRepository.generalReportByDate(startDate, endDate));
        } else {
            model.addAttribute("scores", scoreRepository.generalReportByDepartmentIdAndByDate(departmentId, startDate, endDate));
        }
        return "report/index";
    }

    @PostMapping("/report_filter")
    public String reportFilter(@RequestParam("department_id") Long departmentId, @RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
        Gson gson = new Gson();
        logger.debug("hello this startDate {}", startDate);
        logger.debug("hello this endDate {}", gson.toJson(endDate));
        logger.debug("department id {}", gson.toJson(departmentId));
        if(departmentId == null) {
            return "redirect:/report/report_by/" + 0L + "/" + startDate + "/" + endDate;
        } else {
            return "redirect:/report/report_by/" + departmentId + "/" + startDate + "/" + endDate;
        }
    }

    @RequestMapping("/{departmentId}")
    public String reportByDepartment(@PathVariable Long departmentId, Model model){
        model.addAttribute("scores", scoreRepository.generalReportByDepartment(departmentId));
        logger.debug("Here's total result {} " , scoreRepository.countReportByDepartment(departmentId).getTotal());
        model.addAttribute("department", departmentRepository.findById(departmentId));
        return "report/department";
    }

    @RequestMapping("detail/{departmentId}")
    public String reportDetailByDepartment(@PathVariable Long departmentId, Model model){
        model.addAttribute("scores", scoreRepository.generalReportByDepartment(departmentId));
        model.addAttribute("department", departmentRepository.findById(departmentId));
        return "report/department";
    }



}
