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

import com.tim18.skynet.dto.CompanyDTO;
import com.tim18.skynet.dto.ImageDTO;
import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.RACAdmin;
import com.tim18.skynet.model.RentACar;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.HotelServiceImpl;
import com.tim18.skynet.service.impl.RentACarServiceImpl;

@RestController
public class RACController {

	@Autowired
	private RentACarServiceImpl racService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	
	@GetMapping(value = "/api/getRAC", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getRAC() {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		RentACar rac = user.getRentacar();
		if (rac != null) {
			return new ResponseEntity<>(rac, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/api/editRAC", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> editRAC(@RequestBody CompanyDTO dto) {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		RentACar rac = user.getRentacar();
		if(dto.getAdress().equals("") == false){
			rac.setAddress(dto.getAdress());
		}
		if(dto.getDescription().equals("") == false){
			rac.setDescription(dto.getDescription());
		}
		if(dto.getName().equals("") == false){
			rac.setName(dto.getName());
		}
		user.setRentacar(rac);
		return new ResponseEntity<>(racService.save(rac), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/editRACImage", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> editRACImage(@RequestBody ImageDTO image) {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		RentACar rac = user.getRentacar();
		System.out.println(image.getUrl());
		rac.setImage(image.getUrl());
		user.setRentacar(rac);
		return new ResponseEntity<>(racService.save(rac), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/racs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RentACar> getAll() {
		for(RentACar rac : racService.findAll()){
			System.out.println(rac.getName());
		}
		return racService.findAll();
	}
	
	@RequestMapping(value = "/api/rac", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public RentACar createRAC(@RequestBody RentACar rac) {
		return racService.save(rac);
	}
	
	@RequestMapping(value = "api/racs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getRAC(@PathVariable(value = "id") Long racId) {
		RentACar rac = racService.findOne(racId);

		if (rac == null) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.ok().body(rac);
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
