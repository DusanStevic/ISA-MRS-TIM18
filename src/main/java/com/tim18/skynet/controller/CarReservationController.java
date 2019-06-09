package com.tim18.skynet.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tim18.skynet.model.CarReservation;
import com.tim18.skynet.service.impl.CarReservationServiceImpl;



@Controller
@RequestMapping(value="/vehicleReservations")
public class CarReservationController {

	@Autowired
	private CarReservationServiceImpl vehResService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<CarReservation>> getAll(){
		
		Collection<CarReservation> vehRess = vehResService.findAll();
		
		return new ResponseEntity<>(vehRess, HttpStatus.OK);
	}
	
	
}
