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
import com.tim18.skynet.model.Airline;

@RestController
public class AirlineController {
	
	
	@RequestMapping(value = "/addAirline", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createAirline(@RequestBody CompanyDTO airlineDTO) {
		Airline airline = new Airline(airlineDTO.getName(), airlineDTO.getAdress(), airlineDTO.getDescription());
		//airlineService.save(airline);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
