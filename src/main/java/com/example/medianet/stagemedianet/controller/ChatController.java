package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.entity.Message;
import com.example.medianet.stagemedianet.repository.MessageRepo;
import com.pusher.rest.Pusher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequestMapping("/chat")
@RestController
@CrossOrigin("*")
public class ChatController {
    @Autowired
    private MessageRepo messageRepo;
    @PostMapping("/messages")
    String message(@RequestBody Message message) {
        messageRepo.save(message);
        Pusher pusher = new Pusher("2021877", "78183832176b8fdc87ec", "0bd62ea757ecf736b333");
        pusher.setCluster("eu");
        pusher.setEncrypted(true);

        pusher.trigger("my-channel", "my-event", message);
        return  "message success" + message.toString() ;
    }
    @GetMapping("/get/messages")
    public List<Message> getAllMessages() {
        return messageRepo.findAllByOrderByDateAsc();
    }

}
