package com.tim18.skynet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.model.Airline;
import com.tim18.skynet.repository.AirlinRepository;
import com.tim18.skynet.service.AirlineService;

@Service
public class AirlineServiceImpl implements AirlineService{
	
	@Autowired
	private AirlinRepository userRepository;

	
	public Airline save(Airline airline) {
		return userRepository.save(airline);
	}

	
	public Airline findOne(Long id) {
		return userRepository.getOne(id);
	}

	
	public List<Airline> findAll() {
		return userRepository.findAll();
	}


	public void remove(Long id) {
		userRepository.deleteById(id);
		
	}
	

}
