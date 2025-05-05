package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.Evaluation;
import com.example.medianet.stagemedianet.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository repository;

    public Evaluation save(Evaluation evaluation) {
        return repository.save(evaluation);
    }

    public List<Evaluation> getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
