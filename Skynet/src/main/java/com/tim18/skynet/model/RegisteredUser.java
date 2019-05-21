package com.tim18.skynet.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	
	@OneToMany(mappedBy="vehicle_user",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private Set<CarReservation> vehicleReservations = new HashSet<CarReservation>();
	
	
	

	
	public Set<CarReservation> getVehicleReservations() {
		return vehicleReservations;
	}



	public void setVehicleReservations(Set<CarReservation> vehicleReservations) {
		this.vehicleReservations = vehicleReservations;
	}
	
	public RegisteredUser() {
		// TODO Auto-generated constructor stub
	}
	
	public RegisteredUser( String username, String password, String firstName, String lastName, String email) {
		super(username,password,firstName,lastName,email);
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





}

