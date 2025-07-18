package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.Services.RapportRhService;
import com.example.medianet.stagemedianet.entity.Evaluation;
import com.example.medianet.stagemedianet.repository.EvaluationRepository;
import com.example.medianet.stagemedianet.repository.FormationRepository;
import com.example.medianet.stagemedianet.repository.ObjectifRepository;
import com.example.medianet.stagemedianet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rapport")
@CrossOrigin("*")
public class RapportRhController {

    @Autowired
    private RapportRhService rapportRhService;

    @GetMapping("/rh")
    public ResponseEntity<Map<String, Object>> getRapportRh() {
        Map<String, Object> rapport = rapportRhService.genererRapportRh();
        return ResponseEntity.ok(rapport);
    }
}

