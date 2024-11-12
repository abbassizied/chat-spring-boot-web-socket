package com.sip.ams.controllers;

import com.sip.ams.entities.Message;
import com.sip.ams.entities.User;
import com.sip.ams.services.MessageService;
import com.sip.ams.services.UserService;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo; 
import org.springframework.stereotype.Controller; 

import java.security.Principal; 

@Controller
public class ChatController {

	private final MessageService messageService;
	private final UserService userService;

    public ChatController(MessageService messageService, UserService userService) {
		this.messageService = messageService;
		this.userService = userService;
	}

	/**
     * Handles broadcast messages sent to all users.
     */

    @MessageMapping("/chat.broadcast")
    @SendTo("/topic/broadcast")
    public Message broadcastMessage(Message message, Principal principal) {

		User sender = userService.findByEmail(principal.getName());
        
        // Create and save a broadcast message
		Message newMessage = new Message(sender, message.getMessageContent()); 
        messageService.saveMessage(newMessage);
        
        return newMessage;
    }

 
    /**
     * Handles private messages sent directly to a specific user.
     */
    @MessageMapping("/chat.private")
    @SendTo("/topic/private/{receiverId}")
    public Message privateMessage(Message message, Principal principal, @DestinationVariable Integer receiverId) {
        User sender = userService.findByEmail(principal.getName()); // Get the sender
        User receiver = userService.getUserById(receiverId); // Get the receiver by ID

        // Create and save the private message
        Message newMessage = new Message(sender, message.getMessageContent(), receiver);
        messageService.saveMessage(newMessage);
        
        return newMessage; // Return the new message to be sent to the receiver
    }
	    
    
}
