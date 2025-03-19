package com.example.medianet.stagemedianet.repository;

import com.example.medianet.stagemedianet.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByNom(String nom);

}
