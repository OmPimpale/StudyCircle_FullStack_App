package com.studycircle.studycircle.controller;

import com.studycircle.studycircle.model.Message;
import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.service.MessageService;
import com.studycircle.studycircle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        // Assuming sender and recipient IDs are in the Message object initially
        User sender = userService.findUserById(message.getSender().getId());
        User recipient = userService.findUserById(message.getRecipient().getId());

        if (sender == null || recipient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or a more specific error
        }

        message.setSender(sender);
        message.setRecipient(recipient);
        Message createdMessage = messageService.sendMessage(message);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @GetMapping("/between/{user1Id}/{user2Id}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        User user1 = userService.findUserById(user1Id);
        User user2 = userService.findUserById(user2Id);

        if (user1 == null || user2 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Message> messages = messageService.getMessagesBetweenUsers(user1, user2);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}