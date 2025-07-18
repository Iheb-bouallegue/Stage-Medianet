package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.Evaluation;
import com.example.medianet.stagemedianet.repository.EvaluationRepository;
import com.example.medianet.stagemedianet.repository.FormationRepository;
import com.example.medianet.stagemedianet.repository.ObjectifRepository;
import com.example.medianet.stagemedianet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RapportRhService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private ObjectifRepository objectifRepository;


    public Map<String, Object> genererRapportRh() {
        Map<String, Object> rapport = new HashMap<>();

        // Total utilisateurs
        long totalUsers = userRepository.count();
        rapport.put("totalUsers", totalUsers);

        // Comptage utilisateurs actifs / inactifs (bloqués)
        long totalActifs = userRepository.countByActiveTrue();
        long totalInactifs = userRepository.countByActiveFalse();

        rapport.put("totalActifs", totalActifs);
        rapport.put("totalInactifs", totalInactifs);

        // Récupérer toutes les évaluations
        List<Evaluation> evaluations = evaluationRepository.findAll();

        // Filtrer évaluations valides (avec users non nuls)
        List<Evaluation> evaluationsClean = evaluations.stream()
                .filter(e -> e.getEvaluatedUser() != null && e.getEvaluator() != null)
                .toList();

        int totalEval = evaluationsClean.size();

        // Calcul moyenne score (évite division par zéro)
        double moyenne = evaluationsClean.stream()
                .mapToDouble(Evaluation::getScore)
                .average()
                .orElse(0.0);

        // Calcul haut potentiel (score >= 4 par exemple)
        long hautPotentiel = evaluationsClean.stream()
                .filter(e -> e.getScore() >= 4)
                .count();

        // Calcul en suivi (score >= 2 et < 4)
        long suivi = evaluationsClean.stream()
                .filter(e -> e.getScore() >= 2 && e.getScore() < 4)
                .count();

        // Liste simplifiée des évaluations (map sécurisée)
        List<Map<String, Object>> evaluationsList = evaluationsClean.stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("evaluatedUser", Map.of("username", e.getEvaluatedUser().getUsername()));
                    map.put("evaluator", Map.of("username", e.getEvaluator().getUsername()));
                    map.put("title", e.getTitle());
                    map.put("comments", e.getComments());
                    map.put("score", e.getScore());
                    return map;
                })
                .toList();

        rapport.put("evaluationsList", evaluationsList);

        Map<String, Object> evalStats = new HashMap<>();
        evalStats.put("total", totalEval);
        evalStats.put("moyenne", moyenne);
        evalStats.put("hautPotentiel", hautPotentiel);
        evalStats.put("suivi", suivi);

        rapport.put("evaluations", evalStats);

        // Récupérer formations + nombre participants
        List<Object[]> formationsDataRaw = formationRepository.findFormationsWithParticipantsCount();

        List<String> formationsPopulaires = new ArrayList<>();
        List<Integer> participantsParFormation = new ArrayList<>();

        for(Object[] row : formationsDataRaw) {
            formationsPopulaires.add((String) row[0]);
            participantsParFormation.add((Integer) row[1]);
        }

        Map<String, Object> formationsData = new HashMap<>();
        formationsData.put("populaires", formationsPopulaires);
        formationsData.put("participantsParFormation", participantsParFormation);

        rapport.put("formations", formationsData);



        // Récupérer objectifs non accomplis
        List<String> objectifsList = objectifRepository.findCurrentObjectives();
        rapport.put("objectifs", objectifsList);

        return rapport;
    }
}
