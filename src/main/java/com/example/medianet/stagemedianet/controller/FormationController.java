package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.Services.FormationService;
import com.example.medianet.stagemedianet.entity.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formation")
@CrossOrigin("*")
public class FormationController {
    @Autowired
    private FormationService service;
    @GetMapping("/getall")
    public List<Formation> getAllFormations() {
        return service.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public Formation createFormation(@RequestBody Formation formation) {
        return service.save(formation);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        return service.findById(id).map(f -> {
            formation.setId(id);
            return ResponseEntity.ok(service.save(formation));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    public void deleteFormation(@PathVariable long id) {
        service.deleteById(id);
    }


}
