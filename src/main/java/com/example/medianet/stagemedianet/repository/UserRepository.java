package com.example.medianet.stagemedianet.repository;

import com.example.medianet.stagemedianet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

     Optional<User> findByUsername(String username);
    User findByEmail(String email);
    List<User> findByManager_Id(Long managerId);
    @Query("SELECT u FROM User u WHERE u.role.name = 'MANAGER'")
    List<User> findAllManagers();

    List<User> findByManagerId(Long managerId);

}
