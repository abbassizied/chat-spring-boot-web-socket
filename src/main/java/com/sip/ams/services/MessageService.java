package com.sip.ams.services;

import com.sip.ams.entities.Message;
import com.sip.ams.repositories.MessageRepository;
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
}
