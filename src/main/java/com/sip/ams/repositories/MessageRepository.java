package com.sip.ams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sip.ams.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Additional custom queries can be added here
}
