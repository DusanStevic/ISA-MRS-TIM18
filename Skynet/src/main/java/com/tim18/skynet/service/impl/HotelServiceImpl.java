package com.tim18.skynet.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.repository.HotelRepository;
import com.tim18.skynet.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService{
	
	@Autowired
	private HotelRepository hotelRepository;

	
	public Hotel save(Hotel hotel) {
		return hotelRepository.save(hotel);
	}

	
	public Hotel findOne(Long id) {
		return hotelRepository.getOne(id);
	}

	
	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}


	public void remove(Long id) {
		hotelRepository.deleteById(id);
		
	}


	@Override
	public List<Hotel> search(String name, String address, Date checkin, Date checkout, int beds) {
		if(name != null){
			return hotelRepository.findByNameAndAddressAndDateAndBeds(name, address, checkin, checkout, beds);
		}
		else{
			return hotelRepository.findByAddressAndDateAndBeds(address, checkin, checkout, beds);
		}
	}
	

}
