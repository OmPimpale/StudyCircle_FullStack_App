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
import java.util.Optional; // Import Optional

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
        // Corrected method call and handled Optional return
        Optional<User> senderOptional = userService.getUserById(message.getSender().getId());
        Optional<User> recipientOptional = userService.getUserById(message.getRecipient().getId());

        if (senderOptional.isEmpty() || recipientOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or a more specific error
        }

        User sender = senderOptional.get();
        User recipient = recipientOptional.get();

        message.setSender(sender);
        message.setRecipient(recipient);
        Message createdMessage = messageService.sendMessage(message);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @GetMapping("/between/{user1Id}/{user2Id}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        // Corrected method call and handled Optional return
        Optional<User> user1Optional = userService.getUserById(user1Id);
        Optional<User> user2Optional = userService.getUserById(user2Id);

        if (user1Optional.isEmpty() || user2Optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user1 = user1Optional.get();
        User user2 = user2Optional.get();

        List<Message> messages = messageService.getMessagesBetweenUsers(user1, user2);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
