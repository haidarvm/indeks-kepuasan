package com.haidarvm.indekskepuasan.restControllers;


import com.haidarvm.indekskepuasan.model.Department;
import com.haidarvm.indekskepuasan.model.Score;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScoreRestController {

    private final ScoreRepository scoreRepository;

    public ScoreRestController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }


    @RequestMapping("/score/insert/{departmentId}/{scoreStatisfy}")
    public Map<String, String> score(@PathVariable Long departmentId,  @PathVariable Integer scoreStatisfy) {
        Department dept1 = new Department();
        Score score1 = new Score();
        score1.setScore(scoreStatisfy);
        dept1.setId(departmentId);
        score1.setDepartment(dept1);
        scoreRepository.save(score1);
        return Collections.singletonMap("message", "Hello Terima kasih Telah memberikan Kepuasan");
    }

    @RequestMapping("/score")
    public Map<String, String> hello() {
        String hello = "Hello Terima kasih Atas Kunjungannya :) ";
        return Collections.singletonMap("message", hello);
    }
}