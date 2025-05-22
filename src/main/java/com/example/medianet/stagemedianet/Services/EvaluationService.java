package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.Evaluation;
import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.repository.EvaluationRepository;
import com.example.medianet.stagemedianet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository repository;
    @Autowired
    UserRepository userRepository;

    public Evaluation save(Evaluation evaluation) {
        return repository.save(evaluation);
    }
    public EvaluationService(EvaluationRepository evaluationRepository, UserRepository userRepository) {
        this.repository = evaluationRepository;
        this.userRepository = userRepository;
    }

    public Evaluation saveEvaluation(Long evaluatedUserId, Long evaluatorId, String title, String comments, int score) {
        User evaluatedUser = userRepository.findById(evaluatedUserId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur évalué non trouvé"));
        User evaluator = userRepository.findById(evaluatorId)
                .orElseThrow(() -> new IllegalArgumentException("Evaluateur non trouvé"));

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluatedUser(evaluatedUser);
        evaluation.setEvaluator(evaluator);
        evaluation.setTitle(title);
        evaluation.setComments(comments);
        evaluation.setScore(score);

        return repository.save(evaluation);
    }

}
