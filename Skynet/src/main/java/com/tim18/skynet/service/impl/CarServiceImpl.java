package com.tim18.skynet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.model.Car;
import com.tim18.skynet.repository.CarRepository;
import com.tim18.skynet.service.CarService;

@Service
public class CarServiceImpl implements CarService{
	
	@Autowired
	private CarRepository carRepository;

	
	public Car save(Car car) {
		return carRepository.save(car);
	}

	
	public Car findOne(Long id) {
		return carRepository.getOne(id);
	}

	
	public List<Car> findAll() {
		return carRepository.findAll();
	}


	public void remove(Long id) {
		carRepository.deleteById(id);
		
	}
	

}

