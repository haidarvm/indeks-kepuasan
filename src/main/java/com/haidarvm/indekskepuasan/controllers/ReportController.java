package com.haidarvm.indekskepuasan.controllers;


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

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/report")
public  class ReportController {



    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final ScoreRepository scoreRepository;
    private final DepartmentRepository departmentRepository;

    private String title = "Hello Haidar";

    public ReportController(ScoreRepository scoreRepository, DepartmentRepository departmentRepository) {
        this.scoreRepository = scoreRepository;
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping("")
    public String generalReport(Model model){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("score", new Score());
        model.addAttribute("departments", departmentRepository.findAll());
        List<Score> genReport = scoreRepository.generalReport();
        logger.debug("report generated when it's null by new day {}" , genReport.size());
        model.addAttribute("scores", genReport);
        return "report/index";
    }

    @RequestMapping("/by/{departmentId}/{startDate}/{endDate}")
    public String reportBy(@PathVariable Long departmentId, @PathVariable String startDate, @PathVariable String endDate, Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("deptId", departmentId);
        model.addAttribute("title", "Welcome Guest");
        if(departmentId == 0L) {
            model.addAttribute("scores", scoreRepository.generalReportByDate(startDate, endDate));
        } else {
            model.addAttribute("deptName", departmentRepository.findById(departmentId).get().getName());
            model.addAttribute("scores", scoreRepository.generalReportByDepartmentIdAndByDate(departmentId, startDate, endDate));
        }
        return "report/index";
    }

    @PostMapping("/report_filter")
    public String reportFilter(@RequestParam("department_id") Long departmentId, @RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
        if(null == departmentId) {
            return "redirect:/report/by/" + 0L + "/" + startDate + "/" + endDate;
        } else {
            return "redirect:/report/by/" + departmentId + "/" + startDate + "/" + endDate;
        }
    }

    @RequestMapping("/{departmentId}")
    public String reportByDepartment(@PathVariable Long departmentId, Model model){
        String getDeptName = ((departmentId != 0)) ? departmentRepository.findById(departmentId).get().getName() : null;
        String deptName =  ((getDeptName != null)) ? getDeptName : "Semua Layanan";
        logger.debug("Get Department Name {}", deptName);
        model.addAttribute("deptName ", deptName);
        model.addAttribute("scores", scoreRepository.generalReportByDepartment(departmentId));
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
