package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.Formation;
import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.repository.FormationRepository;
import com.example.medianet.stagemedianet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FormationService {
    private final Map<Long, Set<Long>> reservations = new HashMap<>();
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private UserRepository userRepository;

    public Formation save(Formation formation) {
        return formationRepository.save(formation);
    }
    public List<Formation> findAll() {
        return formationRepository.findAll();
    }
    public Optional<Formation> findById(Long id) {
        return formationRepository.findById(id);
    }

    public void deleteById(Long id) {
        formationRepository.deleteById(id);
    }
    public Formation updateFormation(Long id, Formation updatedFormation) {
        return formationRepository.findById(id).map(existingFormation -> {
            existingFormation.setTitre(updatedFormation.getTitre());
            existingFormation.setDescription(updatedFormation.getDescription());
            existingFormation.setDateDebut(updatedFormation.getDateDebut());
            existingFormation.setDateFin(updatedFormation.getDateFin());
            existingFormation.setPlacesDisponibles(updatedFormation.getPlacesDisponibles());
            return formationRepository.save(existingFormation);
        }).orElseThrow(() -> new RuntimeException("Formation non trouvée avec l'id : " + id));
    }
    public Optional<Formation> reserverPlace(Long formationId, Long utilisateurId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        User utilisateur = userRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si utilisateur a déjà réservé
        if (formation.getUtilisateursReserves().contains(utilisateur)) {
            throw new RuntimeException("Utilisateur a déjà réservé cette formation");
        }

        if (formation.getPlacesDisponibles() == null || formation.getPlacesDisponibles() <= 0) {
            throw new RuntimeException("Plus de places disponibles");
        }

        // Ajouter l'utilisateur à la liste
        formation.getUtilisateursReserves().add(utilisateur);

        // Décrémenter le nombre de places
        formation.setPlacesDisponibles(formation.getPlacesDisponibles() - 1);

        // Sauvegarder la formation mise à jour
        formationRepository.save(formation);

        return Optional.of(formation);
    }





    public List<Formation> getFormationsReserveesParUtilisateur(Long utilisateurId) {
        return formationRepository.findByUtilisateursReserves_Id(utilisateurId);
    }


}
