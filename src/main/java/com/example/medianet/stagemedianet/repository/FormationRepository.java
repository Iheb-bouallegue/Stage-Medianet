package com.example.medianet.stagemedianet.repository;

import com.example.medianet.stagemedianet.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    Formation findByTitre(String name);
    List<Formation> findByUtilisateursReserves_Id(Long utilisateurId);

}
