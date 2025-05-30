package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Message;
import com.studycircle.studycircle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndRecipientOrderByTimestampAsc(User sender, User recipient);

    List<Message> findByRecipientAndSenderOrderByTimestampAsc(User recipient, User sender);
}