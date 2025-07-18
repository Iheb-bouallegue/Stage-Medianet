package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.Formation;
import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.entity.UserProfile;
import com.example.medianet.stagemedianet.repository.FormationRepository;
import com.example.medianet.stagemedianet.repository.UserProfileRepository;
import com.example.medianet.stagemedianet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormationService {
    private final Map<Long, Set<Long>> reservations = new HashMap<>();
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;

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


    private String calculerNiveau(String experience) {
        if (experience == null) return "Intermédiaire";
        String exp = experience.toLowerCase();

        if (exp.contains("junior") || exp.contains("débutant")) {
            return "Débutant";
        } else if (exp.contains("senior") || exp.contains("avancé")) {
            return "Avancé";
        } else {
            int years = 0;
            try {
                String digits = exp.replaceAll("[^0-9]", "");
                if (!digits.isEmpty()) {
                    years = Integer.parseInt(digits);
                }
            } catch (NumberFormatException e) {
                years = 0;
            }

            if (years <= 1) return "Débutant";
            else if (years >= 5) return "Avancé";
            else return "Intermédiaire";
        }
    }
    public List<Formation> suggérerFormationsPour(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId).orElse(null);
        if (profile == null) return Collections.emptyList();

        String poste = Optional.ofNullable(profile.getCurrentPosition()).orElse("").toLowerCase();
        String domaineEtude = Optional.ofNullable(profile.getFieldOfStudy()).orElse("").toLowerCase();
        List<String> competences = Arrays.stream(
                        Optional.ofNullable(profile.getSkills()).orElse("").split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        String niveau = calculerNiveau(profile.getExperience());

        List<Formation> toutesFormations = formationRepository.findAll();

        return toutesFormations.stream()
                .filter(f -> {
                    // Vérifie que le poste correspond
                    boolean matchPoste = f.getCible() != null && f.getCible().equalsIgnoreCase(poste);

                    // Vérifie que le niveau correspond
                    boolean matchNiveau = f.getNiveau() != null && f.getNiveau().equalsIgnoreCase(niveau);

                    // Vérifie que le domaine contient au moins une compétence ou le domaine d’étude
                    boolean matchDomaine = false;
                    if (f.getDomaine() != null) {
                        String d = f.getDomaine().toLowerCase();
                        matchDomaine = competences.stream().anyMatch(d::contains)
                                || d.contains(domaineEtude);
                    }

                    return matchPoste && matchNiveau && matchDomaine;
                })
                .collect(Collectors.toList());
    }





}
