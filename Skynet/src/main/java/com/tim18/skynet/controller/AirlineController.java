package com.tim18.skynet.controller;

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

import com.tim18.skynet.dto.CompanyDTO;
import com.tim18.skynet.model.Airline;
import com.tim18.skynet.service.impl.AirlineServiceImpl;

@RestController
public class AirlineController {
	
	
	@Autowired
	private AirlineServiceImpl airlineService;
	
	@RequestMapping(value = "/addAirline", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Airline createAirline(@Valid @RequestBody CompanyDTO airlineDTO) {
		Airline airline = new Airline(airlineDTO.getName(), airlineDTO.getAdress(), airlineDTO.getDescription());
		return airlineService.save(airline);
	}
}
