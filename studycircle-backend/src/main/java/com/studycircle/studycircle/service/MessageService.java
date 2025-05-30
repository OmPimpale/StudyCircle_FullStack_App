package com.studycircle.studycircle.service;

import com.studycircle.studycircle.model.Message;
import com.studycircle.studycircle.model.User;
import com.studycircle.studycircle.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesBetweenUsers(User user1, User user2) {
        return messageRepository.findBySenderAndRecipientOrRecipientAndSenderOrderByTimestampAsc(user1, user2, user1, user2);
    }
}