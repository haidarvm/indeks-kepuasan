package com.haidarvm.indekskepuasan.controllers;

import com.haidarvm.indekskepuasan.model.Department;
import com.haidarvm.indekskepuasan.model.Score;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
@RequestMapping("/score")
public class ScoreController {

    private final ScoreRepository scoreRepository;

    public ScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @RequestMapping("/insert/{departmentId}")
    public String insertScore(@PathVariable Long departmentId) {
        Department dept1 = new Department();
        Score score1 = new Score();
        score1.setScore(1);
        dept1.setId(departmentId);
        score1.setDepartment(dept1);
        scoreRepository.save(score1);
        return "redirect:/report";
    }

    @RequestMapping("/dissatisfied")
    public String listDissatisfied(Model model) {
        model.addAttribute( "dissatisfied" , scoreRepository.findByScore(-1));
        model.addAttribute("count", scoreRepository.countByScore(-1));
        return "score/dissatisfied";
    }

    @RequestMapping("/dissatisfied/{departmentId}")
    public String listDissatisfiedByDepartment(@PathVariable Long departmentId, Model model) {
        model.addAttribute( "dissatisfied" , scoreRepository.findByScoreAndAndDepartment_Id(-1, departmentId));
        model.addAttribute("count", scoreRepository.countByScoreAndDepartment_Id(-1, departmentId));
        return "score/dissatisfied";
    }

    @RequestMapping("/dissatisfied_date/{departmentId}/{created}")
    public String listDissatisfiedByDateByDepartment(@PathVariable Long departmentId, @PathVariable String created, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(created + " 00:00:00", formatter);
        LocalDateTime endDate = LocalDateTime.parse(created + " 23:59:59", formatter);
        model.addAttribute( "dissatisfied" , scoreRepository.findByScoreAndAndDepartment_IdAndCreatedBetween(-1, departmentId, startDate, endDate));
        model.addAttribute("count", scoreRepository.countByScoreAndDepartment_IdAndCreatedBetween(-1, departmentId, startDate, endDate));
        return "score/dissatisfied";
    }

}

