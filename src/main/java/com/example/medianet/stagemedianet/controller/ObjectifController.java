package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.Services.ObjectifService;
import com.example.medianet.stagemedianet.entity.Objectif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objectifs")
@CrossOrigin(origins = "*")
public class ObjectifController {
    @Autowired
    private ObjectifService objectifService;

    @GetMapping("/utilisateur/{id}")
    public List<Objectif> getObjectifsParUtilisateur(@PathVariable Long id) {
        return objectifService.getObjectifsByUtilisateur(id);
    }

    @PostMapping("/add")
    public Objectif ajouterObjectif(@RequestBody Objectif objectif) {
        return objectifService.ajouterObjectif(objectif);
    }

    @PutMapping("/update")
    public Objectif update(@RequestBody Objectif objectif) {
        return objectifService.mettreAJourObjectif(objectif);
    }

    @DeleteMapping("/delete/{id}")
    public void supprimer(@PathVariable Long id) {
        objectifService.supprimerObjectif(id);
    }
}
