package com.studycircle.studycircle.repository;

import com.studycircle.studycircle.model.Message;
import com.studycircle.studycircle.model.User; // Import User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Import Repository

import java.util.List;

@Repository // Add Repository annotation
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndRecipientOrderByTimestampAsc(User sender, User recipient);

    List<Message> findByRecipientAndSenderOrderByTimestampAsc(User recipient, User sender);

    // Add the method to find messages between two users (sender or recipient)
    List<Message> findBySenderAndRecipientOrRecipientAndSenderOrderByTimestampAsc(User sender, User recipient, User recipient2, User sender2);

    // Note: The parameters in findBySenderAndRecipientOrRecipientAndSenderOrderByTimestampAsc
    // need to match the order and types used in the MessageService method call.
    // In MessageService, it's called with user1, user2, user1, user2.

    // You might also need other custom query methods for messages here
}
