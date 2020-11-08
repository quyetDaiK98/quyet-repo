package com.personal.requestmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor
public class Message {
    private int messageCode;

    private String messageName;

	public int getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public Message() {
		super();
	}

	public Message(int messageCode, String messageName) {
		super();
		this.messageCode = messageCode;
		this.messageName = messageName;
	}
	
}
