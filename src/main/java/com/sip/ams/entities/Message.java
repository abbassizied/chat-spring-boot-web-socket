package com.sip.ams.entities;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.sip.ams.enums.MessageType;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "message_content")
	private String messageContent;
	private ZonedDateTime createdAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "message_type")	
	private MessageType messageType;

	@ManyToOne
	@JoinColumn(name = "from_user_id")
	private User sender;

	@ManyToOne
	@JoinColumn(name = "to_user_id", nullable = true) // Nullable for broadcast or public messages
	private User receiver;

    // Method to return the sender's full name (you can adjust the name format if needed)
    public String getSenderName() {
        return sender != null ? sender.getFirstName() + " " + sender.getLastName() : "Unknown Sender";
    }	
	
	
	// Default constructor
	public Message() {
		// No-arg constructor
	}

	// Constructor for broadcast (public) messages
	public Message(User sender, String messageContent) {
		this.sender = sender;
		this.messageContent = messageContent;
		this.createdAt = ZonedDateTime.now();
		this.messageType = MessageType.PUBLIC_CHAT;
	}

	// Constructor for private messages
	public Message(User sender, String messageContent, User receiver) {
		this.sender = sender;
		this.messageContent = messageContent;
		this.receiver = receiver;
		this.createdAt = ZonedDateTime.now();
		this.messageType = MessageType.PRIVATE_CHAT;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public User getReceiver() {
		return receiver;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	// Format createdAt to user’s local time zone
	public String getFormattedTimestamp(String userTimeZone) {
		return createdAt.withZoneSameInstant(java.time.ZoneId.of(userTimeZone))
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
	}

	// toString for easier logging/debugging
	@Override
	public String toString() {
		return "ChatMessage{" + "id=" + id + ", messageContent='" + messageContent + '\'' + ", createdAt=" + createdAt
				+ ", sender=" + (sender != null ? sender.getEmail() : "null") + ", receiver="
				+ (receiver != null ? receiver.getEmail() : "null") + ", messageType=" + messageType + '}';
	}

}
