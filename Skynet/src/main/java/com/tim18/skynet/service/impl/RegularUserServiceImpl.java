package com.tim18.skynet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.model.RegularUser;
import com.tim18.skynet.repository.RegularUserRepository;
import com.tim18.skynet.service.RegularUserService;

@Service
public class RegularUserServiceImpl implements RegularUserService{
	@Autowired
	private RegularUserRepository regularUserRepository;
	
	
	@Override
	public RegularUser findById(Long id) {
		return regularUserRepository.getOne(id);
	}
}
