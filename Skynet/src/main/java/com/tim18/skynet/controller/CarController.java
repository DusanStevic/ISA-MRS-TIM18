package com.tim18.skynet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tim18.skynet.dto.BranchDTO;
import com.tim18.skynet.dto.CarDTO;
import com.tim18.skynet.model.Branch;
import com.tim18.skynet.model.Car;
import com.tim18.skynet.model.RACAdmin;
import com.tim18.skynet.service.impl.BranchServiceImpl;
import com.tim18.skynet.service.impl.CarServiceImpl;
import com.tim18.skynet.service.impl.CustomUserDetailsService;



@Controller
public class CarController {

	@Autowired
	private CarServiceImpl carService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	

	@RequestMapping( value="/api/car",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Car createCar(@Valid @RequestBody Car car) {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Branch branch = new Branch();
		branch.setRac(user.getRentacar());
		car.setBranch(branch);
		return carService.save(car);
		
	}
	
	@RequestMapping( value="/api/getCars",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Car> getCars() {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Car> cars = new ArrayList<Car>();
		for(Car c : carService.findAll()){
			if(c.getBranch().getRac().getId() == user.getRentacar().getId()){
				cars.add(c);
			}
		}
		return cars;
	}
	
	@RequestMapping( value="/api/getCars/{branch_id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Car> getBranchCars(@PathVariable(value = "branch_id") Long branch_id) {
		List<Car> cars = new ArrayList<Car>();
		for(Car c : carService.findAll()){
			if(c.getBranch().getRac().getId() == branch_id){
				cars.add(c);
			}
		}
		return cars;
	}
	
	@RequestMapping( value="/api/getCar/{branch_id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Car getCar(@PathVariable(value = "car_id") Long car_id) {
		for(Car c : carService.findAll()){
			if(c.getId() == car_id){
				return c;
			}
		}
		return null;
	}
	
	@RequestMapping( value="/api/editCar",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Car editCar(@RequestBody CarDTO car) {
		for(Car c : carService.findAll()){
			if(c.getId() == car.getId()){
				if(car.getGear() != c.getGear()){
					c.setGear(car.getGear());
				}
				if(car.getRegistrationNumber() != c.getRegistrationNumber()){
					c.setRegistrationNumber(car.getRegistrationNumber());
				}
				if(car.getType() != c.getType()) {
					c.setType(car.getType());
				}
				return carService.save(c);
			}
		}
		return null;
	}
	
	@RequestMapping( value="/api/deleteCar/{branch_id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Car deleteCar(@PathVariable(value = "branch_id") Long branch_id) {
		for(Car c : carService.findAll()){
			if(c.getId() == branch_id){
				c.setBranch(null);
			}
		}
		carService.remove(branch_id);
		return null;
	}
}