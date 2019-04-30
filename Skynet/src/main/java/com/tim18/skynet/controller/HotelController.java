package com.tim18.skynet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.HotelServiceImpl;

@RestController
public class HotelController {

	@Autowired
	private HotelServiceImpl hotelService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	
	@GetMapping(value = "/api/getHotel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotelAdmin() {
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Hotel hotel = user.getHotel();
		if (hotel != null) {
			return new ResponseEntity<>(hotel, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/api/hotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> getAll() {
		return hotelService.findAll();
	}
	
	@RequestMapping(value = "api/hotels/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getRAC(@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);

		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.ok().body(hotel);
	}
	
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> updateHotel(@PathVariable(value = "id") Long hotelId,
			@Valid @RequestBody Hotel hotel) {

			Hotel newHotel = hotelService.findOne(hotelId);
			if (newHotel == null) {
				return ResponseEntity.notFound().build();
			}
			for(Hotel h : hotelService.findAll()){
				if(h.getName().equals(hotel.getName())){
					return ResponseEntity.badRequest().build();
				}
			}
			newHotel.setName(hotel.getName());
			newHotel.setAddress(hotel.getAddress());
			newHotel.setDescription(hotel.getDescription());
			
			Hotel updateHotel = hotelService.save(newHotel);
			return ResponseEntity.ok().body(updateHotel);
	}
	
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> deleteHotel(
			@PathVariable(value = "id") Long hotelId) {
			Hotel hotel = hotelService.findOne(hotelId);

			if (hotel != null) {
				hotelService.remove(hotelId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
}
