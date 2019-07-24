package com.haidarvm.indekskepuasan.restControllers;


import com.haidarvm.indekskepuasan.model.Department;
import com.haidarvm.indekskepuasan.model.Score;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScoreRestController {

    private final ScoreRepository scoreRepository;

    public ScoreRestController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }


    @RequestMapping(value="/score/insert/{departmentId}/{scoreStatisfy}/{deviceId}", method = RequestMethod.PUT)
    public Map<String, String> score(@PathVariable Long departmentId,  @PathVariable Integer scoreStatisfy, @PathVariable String deviceId) {
        Department dept1 = new Department();
        Score score1 = new Score();
        score1.setScore(scoreStatisfy);
        dept1.setId(departmentId);
        score1.setDepartment(dept1);
        score1.setDeviceId(deviceId);
        scoreRepository.save(score1);
        return Collections.singletonMap("message", "Terima kasih Telah memberikan Kepuasan");
    }

    @RequestMapping(value="/score/insert/", method = RequestMethod.POST)
    public ResponseEntity<Score> insertScore(@RequestBody Score score) {
        if(score !=null) {
            scoreRepository.save(score);
        }
        return new ResponseEntity(score,HttpStatus.OK);
    }

    @RequestMapping("/score")
    public Map<String, String> hello() {
        String hello = "Hello Terima kasih Atas Kunjungannya :) ";
        return Collections.singletonMap("message", hello);
    }
}
