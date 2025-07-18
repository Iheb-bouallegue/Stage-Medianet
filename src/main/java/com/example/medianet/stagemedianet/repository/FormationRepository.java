package com.example.medianet.stagemedianet.repository;

import com.example.medianet.stagemedianet.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    Formation findByTitre(String name);
    List<Formation> findByUtilisateursReserves_Id(Long utilisateurId);
    List<Formation> findByCibleIgnoreCase(String cible);

    List<Formation> findByDomaineContainingIgnoreCase(String domaine);

    List<Formation> findByNiveauIgnoreCase(String niveau);


    @Query("SELECT f FROM Formation f WHERE " +
            "LOWER(f.cible) = LOWER(:poste) AND " +
            "(LOWER(f.domaine) LIKE LOWER(CONCAT('%', :competence, '%')) OR LOWER(f.domaine) LIKE LOWER(CONCAT('%', :domaineEtude, '%'))) AND " +
            "LOWER(f.niveau) = LOWER(:niveau)")
    List<Formation> findFormationsByAllCriteria(@Param("poste") String poste,
                                                @Param("competence") String competence,
                                                @Param("domaineEtude") String domaineEtude,
                                                @Param("niveau") String niveau);

    @Query("SELECT f.titre, SIZE(f.utilisateursReserves) FROM Formation f ORDER BY SIZE(f.utilisateursReserves) DESC")
    List<Object[]> findFormationsWithParticipantsCount();

}

