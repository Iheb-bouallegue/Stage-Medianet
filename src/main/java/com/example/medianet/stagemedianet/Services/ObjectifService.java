package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.Objectif;
import com.example.medianet.stagemedianet.repository.ObjectifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectifService {
    @Autowired
    private ObjectifRepository objectifRepository;

    public List<Objectif> getObjectifsByUtilisateur(Long utilisateurId) {
        return objectifRepository.findByUtilisateurId(utilisateurId);
    }

    public Objectif ajouterObjectif(Objectif objectif) {
        return objectifRepository.save(objectif);
    }

    public void supprimerObjectif(Long id) {
        objectifRepository.deleteById(id);
    }

    public Objectif mettreAJourObjectif(Objectif objectif) {
        return objectifRepository.save(objectif);
    }
}

