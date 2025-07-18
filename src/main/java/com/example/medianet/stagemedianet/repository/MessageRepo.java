package com.example.medianet.stagemedianet.repository;

import com.example.medianet.stagemedianet.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderByDateAsc(); // pour trier les messages


    List<Message> findAll();
}
