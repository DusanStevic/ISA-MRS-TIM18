package com.tim18.skynet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.tim18.skynet.model.Airline;
import com.tim18.skynet.service.impl.AirlineServiceImpl;

@RestController
public class AirlineController {
	
	
	@Autowired
	private AirlineServiceImpl airlineService;
	
//	@RequestMapping(value = "/addAirline", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
//	public Airline createAirline(@Valid @RequestBody CompanyDTO airlineDTO) {
//		Airline airline = new Airline(airlineDTO.getName(), airlineDTO.getAdress(), airlineDTO.getDescription());
//		return airlineService.save(airline);
//	}
	
	
	@RequestMapping( value="api/airline",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Airline createAirline(@Valid @RequestBody Airline airline) {
		return airlineService.save(airline);
	}
	
	
	

	
	@RequestMapping(value = "api/airlines", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Airline> getAllAirlines() {
		return airlineService.findAll();
	}

	
	@RequestMapping(value = "/api/airlines/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> getAirline(
			@PathVariable(value = "id") Long AirlineId) {
		Airline airline = airlineService.findOne(AirlineId);

		if (airline == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(airline);
	}

	
	@RequestMapping(value = "/api/airlines/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> updateAirline(
			@PathVariable(value = "id") Long AirlineId,
			@Valid @RequestBody Airline a) {

		Airline airline = airlineService.findOne(AirlineId);
		if (airline == null) {
			return ResponseEntity.notFound().build();
		}

		airline.setName(a.getName());
		airline.setAddress(a.getAddress());
		airline.setDescription(a.getDescription());
		

		Airline updateAirline = airlineService.save(airline);
		return ResponseEntity.ok().body(updateAirline);
	}

	
	@RequestMapping(value = "/api/airlines/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> deleteAirline(
			@PathVariable(value = "id") Long AirlineId) {
		Airline airline = airlineService.findOne(AirlineId);

		if (airline != null) {
			airlineService.remove(AirlineId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
