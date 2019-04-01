package com.tim18.skynet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.tim18.skynet.model.Car;
import com.tim18.skynet.service.impl.CarServiceImpl;

@RestController
public class CarController {

	@Autowired
	private CarServiceImpl carService;
	
	

	
	@RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Car createCar(@Valid @RequestBody Car car) {
		return carService.save(car);
	}
	
	
	

	
	@RequestMapping(value = "/api/cars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Car> getAllCars() {
		return carService.findAll();
	}

	
	@RequestMapping(value = "/api/cars/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> getCar(
			@PathVariable(value = "id") Long CarId) {
		Car car = carService.findOne(CarId);

		if (car == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(car);
	}

	
	@RequestMapping(value = "/api/cars/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> updateCar(
			@PathVariable(value = "id") Long CarId,
			@Valid @RequestBody Car c) {

		Car car = carService.findOne(CarId);
		if (car == null) {
			return ResponseEntity.notFound().build();
		}

		car.setRegistrationNumber(c.getRegistrationNumber());
		car.setType(c.getType());
		car.setGear(c.getGear());
		
		
		Car updateCar = carService.save(car);
		return ResponseEntity.ok().body(updateCar);
	}

	
	@RequestMapping(value = "/api/casr/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> deleteCar(
			@PathVariable(value = "id") Long CarId) {
		Car car = carService.findOne(CarId);

		if (car != null) {
			carService.remove(CarId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
