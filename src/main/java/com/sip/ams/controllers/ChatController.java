package com.sip.ams.controllers;

import com.sip.ams.entities.Message;
import com.sip.ams.entities.User;
import com.sip.ams.repositories.MessageRepository;
import com.sip.ams.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("message", "Welcome to the Chatroom!");
        return "pages/index"; // Loads the main chat page
    }

    /**
     * Handles broadcast messages sent to all users.
     */
    @MessageMapping("/chat.broadcast")
    @SendTo("/topic/broadcast")
    public Message broadcastMessage(Message message) {
        User sender = getOrCreateUser(message.getFromUser().getUsername());
        
        // Create and save a broadcast message
        Message savedMessage = new Message(sender, message.getText());
        messageRepository.save(savedMessage);
        
        return new Message(sender, message.getText());
    }

    /**
     * Handles private messages sent directly to a specific user.
     */
    @MessageMapping("/chat.private")
    @SendTo("/topic/private")
    public Message privateMessage(Message message) {
        User sender = getOrCreateUser(message.getFromUser().getUsername());
        User recipient = getOrCreateUser(message.getToUser().getUsername());

        // Create and save a private message
        Message savedMessage = new Message(sender, message.getText(), recipient);
        messageRepository.save(savedMessage);
        
        return new Message(sender, message.getText(), recipient);
    }

    /**
     * Retrieves an existing user by username or creates a new user if none exists.
     */
    private User getOrCreateUser(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.orElseGet(() -> {
            User newUser = new User(username);
            userRepository.save(newUser);
            return newUser;
        });
    }
}
