package com.tim18.skynet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class FriendRequest {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id")
	private RegisteredUser sender;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reciever_id")
	private RegisteredUser reciever;
	
	@Column(name = "accepted")
	private Boolean accepted;
	
	public FriendRequest() {
		// TODO Auto-generated constructor stub
	}

	public FriendRequest(RegisteredUser sender, RegisteredUser reciever, boolean accepted) {
		super();
		this.sender = sender;
		this.reciever = reciever;
		this.accepted = accepted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getSender() {
		return sender;
	}

	public void setSender(RegisteredUser sender) {
		this.sender = sender;
	}

	public RegisteredUser getReciever() {
		return reciever;
	}

	public void setReciever(RegisteredUser reciever) {
		this.reciever = reciever;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	
	

	
	

}
