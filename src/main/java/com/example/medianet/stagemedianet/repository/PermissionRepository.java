package com.example.medianet.stagemedianet.repository;

import com.example.medianet.stagemedianet.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByNom(String nom);

}
