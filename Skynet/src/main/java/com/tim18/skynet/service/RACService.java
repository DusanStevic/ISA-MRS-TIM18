package com.tim18.skynet.service;

import java.util.List;

import com.tim18.skynet.model.RentACar;

public interface RACService {
	public RentACar save(RentACar rac);
	public RentACar findOne(Long id);
	public List<RentACar> findAll();
	public void remove(Long id);
}
