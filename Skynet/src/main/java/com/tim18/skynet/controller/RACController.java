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
import com.tim18.skynet.service.impl.RentACarServiceImpl;

@RestController
public class RACController {

	@Autowired
	private RentACarServiceImpl racService;
	
	
	@RequestMapping( value="/api/racs",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public RentACar createRentAcar(@Valid @RequestBody RentACar rac) {
		return racService.save(rac);
	}
	

	@RequestMapping(value = "/api/racs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RentACar> getAll() {
		return racService.findAll();
	}


	@RequestMapping(value = "/api/racs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getRAC(
			@PathVariable(value = "id") Long racId) {
			RentACar rac = racService.findOne(racId);

			if (rac == null) {
				return ResponseEntity.notFound().build();
			}
		
			return ResponseEntity.ok().body(rac);
	}


	@RequestMapping(value = "/api/racs/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> updateRAC(
			@PathVariable(value = "id") Long racId,
			@Valid @RequestBody RentACar c) {

			RentACar rac = racService.findOne(racId);
			if (rac == null) {
				return ResponseEntity.notFound().build();
			}

			rac.setName(c.getName());
			rac.setAddress(c.getAddress());
			rac.setDescription(c.getDescription());
				
				
				

			RentACar updateRAC = racService.save(rac);
			return ResponseEntity.ok().body(updateRAC);
	}


	@RequestMapping(value = "/api/racs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> deleteRAC(
			@PathVariable(value = "id") Long racId) {
			RentACar rac = racService.findOne(racId);

			if (rac != null) {
				racService.remove(racId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	}

	
	
	

