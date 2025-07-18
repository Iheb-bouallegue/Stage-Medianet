package com.example.medianet.stagemedianet.repository;

import com.example.medianet.stagemedianet.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByEvaluatedUserId(Long utilisateurId);
    @Query("SELECT AVG(e.score) FROM Evaluation e")
    Double moyenneNoteGlobale();


}
