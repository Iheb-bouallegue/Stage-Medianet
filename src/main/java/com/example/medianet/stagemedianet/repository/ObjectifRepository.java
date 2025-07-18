package com.example.medianet.stagemedianet.repository;

import com.example.medianet.stagemedianet.entity.Objectif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ObjectifRepository extends JpaRepository<Objectif, Long> {
    List<Objectif> findByUtilisateurId(Long utilisateurId);

    @Query("SELECT o.titre FROM Objectif o WHERE o.accompli = false")
    List<String> findCurrentObjectives();

}
