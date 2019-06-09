package com.tim18.skynet.service;

import java.util.List;

import com.tim18.skynet.model.Airline;

public interface AirlineService {
	public Airline save(Airline airline);
	public Airline findOne(Long id);
	public List<Airline> findAll();
	public void remove(Long id);
}
