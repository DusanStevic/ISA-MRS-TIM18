package com.tim18.skynet.service.impl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.model.CarReservation;
import com.tim18.skynet.repository.CarReservationRepository;


@Service
public class CarReservationServiceImpl {

	@Autowired
	private  CarReservationRepository vehicleReservationRepository;
	
	public Optional<CarReservation> findOne(Long id){
		return vehicleReservationRepository.findById(id);
	}
	
	public List<CarReservation> findAll(){
		return vehicleReservationRepository.findAll();
	}
	
	public CarReservation save(CarReservation vehRes) {
		return vehicleReservationRepository.save(vehRes);
	}
	
	public void delete(Long id) {
		vehicleReservationRepository.deleteById(id);
	}
}
