package social_media_platform.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import social_media_platform.app.models.Message;
import social_media_platform.app.models.User;
import social_media_platform.app.repositories.MessageRepository;
import social_media_platform.app.repositories.UserRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> getMessages(String senderEmail, String receiverEmail) {
        Optional<User> senderOptional = userRepository.findById(senderEmail);
        Optional<User> receiverOptional = userRepository.findById(receiverEmail);

        if (senderOptional.isEmpty() || receiverOptional.isEmpty()) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }

        User sender = senderOptional.get();
        User receiver = receiverOptional.get();

        // Use the new repository method to fetch all messages between the two users
        List<Message> messages = messageRepository.findConversation(sender, receiver);

        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

    public ResponseEntity<?> getLatestMessage(String userEmail1, String userEmail2) {
        Optional<User> user1 = userRepository.findById(userEmail1);
        Optional<User> user2 = userRepository.findById(userEmail2);

        if (user1.isEmpty() || user2.isEmpty()) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(0, 1);
        List<Message> messages = messageRepository.findLatestMessageBetween(user1.get(), user2.get(), pageable);

        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(messages.get(0), HttpStatus.OK);
    }

    public ResponseEntity<?> sendMessage(String senderEmail, String receiverEmail, String content) {
        Optional<User> senderOptional = userRepository.findById(senderEmail);
        Optional<User> receiverOptional = userRepository.findById(receiverEmail);

        if (senderOptional.isEmpty() || receiverOptional.isEmpty()) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }

        User sender = senderOptional.get();
        User receiver = receiverOptional.get();

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setTimestamp(new Timestamp(Instant.now().toEpochMilli()));

        messageRepository.save(message);
        return new ResponseEntity<Message>(message, HttpStatus.CREATED);
    }
} 