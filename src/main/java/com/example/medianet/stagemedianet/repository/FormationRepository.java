package com.example.medianet.stagemedianet.repository;

import com.example.medianet.stagemedianet.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    Formation findByTitre(String name);
}
