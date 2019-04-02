package com.tim18.skynet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.model.Airline;
import com.tim18.skynet.repository.AirlineRepository;
import com.tim18.skynet.service.AirlineService;

@Service
public class AirlineServiceImpl implements AirlineService{
	
	@Autowired
	private AirlineRepository airlineRepository;

	
	public Airline save(Airline airline) {
		return airlineRepository.save(airline);
	}

	
	public Airline findOne(Long id) {
		return airlineRepository.getOne(id);
	}

	
	public List<Airline> findAll() {
		return airlineRepository.findAll();
	}


	public void remove(Long id) {
		airlineRepository.deleteById(id);
		
	}
	

}
