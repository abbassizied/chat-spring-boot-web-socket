package com.sip.ams.services;

import com.sip.ams.entities.Message;
import com.sip.ams.entities.User;
import com.sip.ams.enums.MessageType;
import com.sip.ams.repositories.MessageRepository;

import java.util.List;

import org.springframework.stereotype.Service;
  
@Service
public class MessageService {
    private final MessageRepository messageRepository;


    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Create a new message
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
    
    // Retrieve all messages by message type (Reusable method)
    public List<Message> getMessagesByType(MessageType messageType) {
        return messageRepository.findAllMessagesByType(messageType);
    }
 
    // Retrieve all private messages between two users
    public List<Message> getPrivateMessages(User sender, User receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }    
}
