package com.sip.ams.models;

public class ChatNotification {
	
	private String id;
	private String senderId;
	private String recipientId;
	private String content;

	public ChatNotification() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ChatNotification [id=" + id + ", senderId=" + senderId + ", recipientId=" + recipientId + ", content="
				+ content + "]";
	}

}
