package com.tim18.skynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.CompanyDTO;
import com.tim18.skynet.model.Hotel;

@RestController
public class HotelController {

	@RequestMapping(value = "/addHotel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createHotel(@RequestBody CompanyDTO hotelDTO) {
		Hotel hotel = new Hotel(hotelDTO.getName(), hotelDTO.getAdress(), hotelDTO.getDescription());
		//hotelService.save(hotel);
		System.out.println(hotel.getName());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
