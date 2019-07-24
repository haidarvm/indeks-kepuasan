package com.haidarvm.indekskepuasan.restControllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidarvm.indekskepuasan.model.Score;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

//
//    @RequestMapping(value="/score/insert/{departmentId}/{scoreSatisfy}/{deviceId}", method = RequestMethod.PUT)
//    public Map<String, String> score(@PathVariable Long departmentId,  @PathVariable Integer scoreStatisfy, @PathVariable String deviceId) {
//        Department dept1 = new Department();
//        Score score1 = new Score();
//        score1.setScore(scoreSatisfy);
//        dept1.setId(departmentId);
//        score1.setDepartment(dept1);
//        score1.setDeviceId(deviceId);
//        scoreRepository.save(score1);
//        return Collections.singletonMap("message", "Terima kasih Telah memberikan Kepuasan");
//    }

//    @RequestMapping(value="/score/insert", method = RequestMethod.POST, produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
    @PostMapping("/score")
    Score newScore(@RequestBody Score newScore)  throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        System.out.println("Object mapper "+mapper.writeValueAsString(newScore));
        return scoreRepository.save(newScore);
    }

    @RequestMapping("/score/hello")
    public Map<String, String> hello() {
        String hello = "Hello Terima kasih Atas Kunjungannya :) ";
        return Collections.singletonMap("message", hello);
    }
}
