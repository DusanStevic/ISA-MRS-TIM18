package com.tim18.skynet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.service.impl.HotelServiceImpl;

@RestController
public class HotelController {

	@Autowired
	private HotelServiceImpl hotelService;
	
	

	
	@RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Hotel createHotel(@Valid @RequestBody Hotel hotel) {
		return hotelService.save(hotel);
	}
	
	
	

	
	@RequestMapping(value = "/api/hotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> getAllHotels() {
		return hotelService.findAll();
	}

	
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(
			@PathVariable(value = "id") Long HotelId) {
		Hotel hotel = hotelService.findOne(HotelId);

		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(hotel);
	}

	
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> updateHotel(
			@PathVariable(value = "id") Long HotelId,
			@Valid @RequestBody Hotel h) {

		Hotel hotel = hotelService.findOne(HotelId);
		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}

		hotel.setName(h.getName());
		hotel.setAddress(h.getAddress());
		hotel.setDescription(h.getDescription());

		Hotel updateHotel = hotelService.save(hotel);
		return ResponseEntity.ok().body(updateHotel);
	}

	
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> deleteHotel(
			@PathVariable(value = "id") Long HotelId) {
		Hotel hotel = hotelService.findOne(HotelId);

		if (hotel != null) {
			hotelService.remove(HotelId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
