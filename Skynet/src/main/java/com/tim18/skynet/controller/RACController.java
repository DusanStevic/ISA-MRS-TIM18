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


import com.tim18.skynet.model.RentACar;
import com.tim18.skynet.service.impl.RACServiceImpl;

@RestController
public class RACController {

	@Autowired
	private RACServiceImpl racService;
	
	

	
	@RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public RentACar createRentAcar(@Valid @RequestBody RentACar rac) {
		return racService.save(rac);
	}
	
	
	

	
	@RequestMapping(value = "/api/racs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RentACar> getAllRentAcars() {
		return racService.findAll();
	}

	
	@RequestMapping(value = "/api/racs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getRentAcar(
			@PathVariable(value = "id") Long racId) {
		RentACar rentACar = racService.findOne(racId);

		if (rentACar == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(rentACar);
	}

	
	@RequestMapping(value = "/api/racs/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> updateRentAcar(
			@PathVariable(value = "id") Long racId,
			@Valid @RequestBody RentACar r) {

		RentACar rentAcar = racService.findOne(racId);
		if (rentAcar == null) {
			return ResponseEntity.notFound().build();
		}

		rentAcar.setName(r.getName());
		rentAcar.setAddress(r.getAddress());
		rentAcar.setDescription(r.getDescription());

		RentACar updateRAC = racService.save(rentAcar);
		return ResponseEntity.ok().body(updateRAC);
	}

	
	@RequestMapping(value = "/api/cars/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> deleteRentAcar(
			@PathVariable(value = "id") Long RACId) {
		RentACar rentACar = racService.findOne(RACId);

		if (rentACar != null) {
			racService.remove(RACId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
