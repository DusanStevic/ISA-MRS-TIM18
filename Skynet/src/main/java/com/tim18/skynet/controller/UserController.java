package com.tim18.skynet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.model.AirlineAdmin;
import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.RACAdmin;
import com.tim18.skynet.model.User;
import com.tim18.skynet.security.TokenHelper;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.UserServiceImpl;

@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	TokenHelper tokenUtils;

	@Autowired
	private CustomUserDetailsService userInfoService;
	
	@GetMapping(value = "/api/getRegUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getLogged() {
		User user = (User) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = "api/getAirlineAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineAdmin> getAirlineAdmin() {
		User user = (User) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		AirlineAdmin retVal = (AirlineAdmin) user;
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	@GetMapping(value = "api/getRACAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RACAdmin> getRACAdmin() {
		User user = (User) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		RACAdmin retVal = (RACAdmin) user;
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}
	
	
	
	
	
	
	@RequestMapping( value="api/users",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public User createUser(@Valid @RequestBody User user) {
		return userService.save(user);
	}
	

	
	@RequestMapping(value = "/api/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(
			@PathVariable(value = "id") Long UserId) {
		User user = userService.findOne(UserId);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(user);
	}

	
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(
			@PathVariable(value = "id") Long UserId,
			@Valid @RequestBody User u) {

		User user = userService.findOne(UserId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		user.setName(u.getName());
		user.setSurname(u.getSurname());
		

		User updateUser = userService.save(user);
		return ResponseEntity.ok().body(updateUser);
	}

	
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> deleteUser(
			@PathVariable(value = "id") Long UserId) {
		User user = userService.findOne(UserId);

		if (user != null) {
			userService.remove(UserId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
