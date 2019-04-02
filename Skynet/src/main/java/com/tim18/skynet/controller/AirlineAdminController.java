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

import com.tim18.skynet.model.Airline;
import com.tim18.skynet.model.AirlineAdmin;
import com.tim18.skynet.dto.AdminDTO;
import com.tim18.skynet.service.impl.AirlineAdminServiceImpl;
import com.tim18.skynet.service.impl.AirlineServiceImpl;

@RestController
//@RequestMapping(value="api/airlineAdmins")
public class AirlineAdminController {

	@Autowired
	private AirlineAdminServiceImpl airlineAdminService;
	
	

	
	@RequestMapping(value = "/addAirlineAdmin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public AirlineAdmin createAirlineAdmin(@RequestBody AdminDTO admin) {
		Airline airline = null;
		//ZA SAD OVAKO POSLE LEPSE
		List<Airline> list = new AirlineServiceImpl().findAll();
		for(Airline a : list){
			if(a.getName().equals(admin.getCompany())){
				airline = a;
			}
		}
		AirlineAdmin user = new AirlineAdmin(admin.getName(), admin.getSurname(), admin.getUsername(), admin.getEmail(), admin.getPassword(), airline);
		return airlineAdminService.save(user);
	}
	
	
	

	
	@RequestMapping(value = "/api/airlineAdmins", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AirlineAdmin> getAllAirlineAdmin() {
		return airlineAdminService.findAll();
	}

	
	@RequestMapping(value = "/api/airlineAdmins/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineAdmin> getAirlineAdmin(
			@PathVariable(value = "id") Long AirlineAdminId) {
		AirlineAdmin airlineAdmin = airlineAdminService.findOne(AirlineAdminId);

		if (airlineAdmin == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(airlineAdmin);
	}

	
	@RequestMapping(value = "/api/airlineAdmins/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineAdmin> updateAirlineAdmin(
			@PathVariable(value = "id") Long AirlineAdminId,
			@Valid @RequestBody AirlineAdmin a) {

		AirlineAdmin airlineAdmin = airlineAdminService.findOne(AirlineAdminId);
		if (airlineAdmin == null) {
			return ResponseEntity.notFound().build();
		}

		airlineAdmin.setName(a.getName());
		airlineAdmin.setSurname(a.getSurname());
		
		airlineAdmin.setEmail(a.getEmail());
		airlineAdmin.setPassword(a.getPassword());
		airlineAdmin.setUsername(a.getUsername());
		
		

		AirlineAdmin updateAirlineAdmin = airlineAdminService.save(airlineAdmin);
		return ResponseEntity.ok().body(updateAirlineAdmin);
	}

	
	@RequestMapping(value = "/api/airlineAdmins/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineAdmin> deleteAirlineAdmin(
			@PathVariable(value = "id") Long AirlineAdminId) {
		AirlineAdmin airlineAdmin = airlineAdminService.findOne(AirlineAdminId);

		if (airlineAdmin != null) {
			airlineAdminService.remove(AirlineAdminId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}



