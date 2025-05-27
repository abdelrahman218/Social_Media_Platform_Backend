package social_media_platform.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_media_platform.app.services.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    // DTO for receiving message text from frontend
    public static class MessageRequest {
        public String text;
    }

    @GetMapping
    public ResponseEntity<?> getMessages(
            @RequestParam String senderEmail,
            @RequestParam String receiverEmail) {
        return messageService.getMessages(senderEmail, receiverEmail);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestMessage(
            @RequestParam String userEmail1,
            @RequestParam String userEmail2) {
        return messageService.getLatestMessage(userEmail1, userEmail2);
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(
            @RequestParam String senderEmail,
            @RequestParam String receiverEmail,
            @RequestBody MessageRequest request) {
        return messageService.sendMessage(senderEmail, receiverEmail, request.text);
    }
} 