package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.Services.EvaluationService;
import com.example.medianet.stagemedianet.entity.Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService service;
    @PostMapping("/save")
    public ResponseEntity<Evaluation> create(@RequestBody Evaluation evaluation) {
        return ResponseEntity.ok(service.save(evaluation));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Evaluation>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

}
