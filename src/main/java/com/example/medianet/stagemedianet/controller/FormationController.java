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

    @PutMapping("/update/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        return service.findById(id).map(f -> {
            formation.setId(id);
            return ResponseEntity.ok(service.save(formation));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFormation(@PathVariable long id) {
        service.deleteById(id);
    }
    @PutMapping("/reserver/{id}")
    public ResponseEntity<Formation> reserverUnePlace(@PathVariable Long id, @RequestParam Long utilisateurId) {
        try {
            return service.reserverPlace(id, utilisateurId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }
    @GetMapping("/mes-reservations/{utilisateurId}")
    public List<Formation> getMesFormationsReservees(@PathVariable Long utilisateurId) {
        return service.getFormationsReserveesParUtilisateur(utilisateurId);
    }

    @GetMapping("/suggestion/{userId}")
    public ResponseEntity<List<Formation>> suggérerFormations(@PathVariable Long userId) {
        List<Formation> formations = service.suggérerFormationsPour(userId);
        return ResponseEntity.ok(formations);
    }



}
