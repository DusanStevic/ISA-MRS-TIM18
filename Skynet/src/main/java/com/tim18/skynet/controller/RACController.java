package com.tim18.skynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.CompanyDTO;
import com.tim18.skynet.model.RentACar;
import com.tim18.skynet.service.impl.RentACarServiceImpl;

@RestController
public class RACController {

	@Autowired
	private RentACarServiceImpl racService;
	
	@RequestMapping(value = "/addRentCar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public RentACar createRentAcar(@RequestBody CompanyDTO racDTO) {
		RentACar rac = new RentACar(racDTO.getName(), racDTO.getAdress(), racDTO.getDescription());
		return racService.save(rac);
	}
}
