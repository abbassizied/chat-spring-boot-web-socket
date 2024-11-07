package com.sip.ams.entities;

import jakarta.persistence.*; 
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String timestamp;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = true) // Nullable for broadcast messages
    private User toUser;

    public Message() {
        // Default constructor
    }

    // Constructor for creating broadcast messages
    public Message(User fromUser, String text) {
        this.fromUser = fromUser;
        this.text = text;
        this.timestamp = getCurrentTimestamp();
    }

    // Constructor for creating private messages
    public Message(User fromUser, String text, User toUser) {
        this.fromUser = fromUser;
        this.text = text;
        this.toUser = toUser;
        this.timestamp = getCurrentTimestamp();
    }

    // Getters for each field
    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    // Helper method to generate a timestamp
    private String getCurrentTimestamp() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }
}
