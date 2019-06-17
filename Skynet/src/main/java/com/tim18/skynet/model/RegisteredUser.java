package com.tim18.skynet.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class RegisteredUser extends User {
	
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "sender")
	private Set<FriendRequest> sendRequests = new HashSet<FriendRequest>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "reciever")
	private Set<FriendRequest> receivedRequests = new HashSet<FriendRequest>();

	@ManyToMany(mappedBy = "passangers", cascade = CascadeType.REFRESH)
	private List<Reservation> reservations = new ArrayList<Reservation>(); 
	
	public RegisteredUser() {
		// TODO Auto-generated constructor stub
	}
	
	public RegisteredUser( String username, String password, String firstName, String lastName, String email) {
		super(username,password,firstName,lastName,email);
	}

	
	@JsonIgnore
	@OneToMany(mappedBy = "registredUser", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<CarReservation> carReservations = new ArrayList<>();


	public List<CarReservation> getCarReservations() {
		return carReservations;
	}

	public void setCarReservations(List<CarReservation> carReservations) {
		this.carReservations = carReservations;
	}

	public Set<FriendRequest> getSendRequests() {
		return sendRequests;
	}

	public void setSendRequests(Set<FriendRequest> sendRequests) {
		this.sendRequests = sendRequests;
	}

	public Set<FriendRequest> getReceivedRequests() {
		return receivedRequests;
	}

	public void setReceivedRequests(Set<FriendRequest> receivedRequests) {
		this.receivedRequests = receivedRequests;
	}
	
	
	


	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
    @JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
    @JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	



}

