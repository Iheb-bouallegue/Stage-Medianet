package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.Permission;
import com.example.medianet.stagemedianet.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {



  @Autowired
  PermissionRepository permissionRepository;

        public PermissionService(PermissionRepository permissionRepository) {
            this.permissionRepository = permissionRepository;
        }

        public List<Permission> getAllPermissions() {
            return permissionRepository.findAll();
        }

        public Optional<Permission> getPermissionById(Long id) {
            return permissionRepository.findById(id);
        }

        public Permission createPermission(Permission permission) {
            return permissionRepository.save(permission);
        }

        public void deletePermission(Long id) {
            permissionRepository.deleteById(id);
        }
    }


