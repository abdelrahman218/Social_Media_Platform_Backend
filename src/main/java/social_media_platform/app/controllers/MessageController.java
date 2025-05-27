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

    @GetMapping
    public ResponseEntity<?> getMessages(
            @RequestParam String senderEmail,
            @RequestParam String receiverEmail) {
        return messageService.getMessages(senderEmail, receiverEmail);
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(
            @RequestParam String senderEmail,
            @RequestParam String receiverEmail,
            @RequestBody String content) {
        return messageService.sendMessage(senderEmail, receiverEmail, content);
    }
} 