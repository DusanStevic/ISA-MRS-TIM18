package com.tim18.skynet.service;

import java.util.List;

import com.tim18.skynet.model.Hotel;


public interface HotelService {
	public Hotel save(Hotel hotel);
	public Hotel findOne(Long id);
	public List<Hotel> findAll();
	public void remove(Long id);

}
