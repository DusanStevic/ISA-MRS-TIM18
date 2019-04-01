package com.tim18.skynet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.model.RentACar;
import com.tim18.skynet.repository.RACRepository;
import com.tim18.skynet.service.RACService;

@Service
public class RACServiceImpl implements RACService{
	
	@Autowired
	private RACRepository racRepository;

	
	public RentACar save(RentACar rac) {
		return racRepository.save(rac);
	}

	
	public RentACar findOne(Long id) {
		return racRepository.getOne(id);
	}

	
	public List<RentACar> findAll() {
		return racRepository.findAll();
	}


	public void remove(Long id) {
		racRepository.deleteById(id);
		
	}
	

}

