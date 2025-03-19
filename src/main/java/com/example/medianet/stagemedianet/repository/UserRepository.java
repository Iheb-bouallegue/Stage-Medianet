package com.example.medianet.stagemedianet.reposotory;

import com.example.medianet.stagemedianet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
    User findByEmail(String email);
}
