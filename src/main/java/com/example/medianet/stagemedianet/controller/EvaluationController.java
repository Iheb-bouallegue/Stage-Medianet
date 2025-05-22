package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.Services.EvaluationService;
import com.example.medianet.stagemedianet.entity.Evaluation;
import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.repository.EvaluationRepository;
import com.example.medianet.stagemedianet.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @PostMapping("/save")

    public ResponseEntity<Evaluation> createEvaluation(@RequestBody Evaluation evaluation) {
        Evaluation savedEvaluation = service.save(evaluation);
        return ResponseEntity.ok(savedEvaluation);

    }
    @GetMapping("/mes-evaluations")
    public List<Evaluation> getMesEvaluations(@RequestParam Long utilisateurId) {
        return evaluationRepository.findByEvaluatedUserId(utilisateurId);
    }







}
