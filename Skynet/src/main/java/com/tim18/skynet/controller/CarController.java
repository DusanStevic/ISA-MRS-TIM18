package com.tim18.skynet.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tim18.skynet.dto.BranchDTO;
import com.tim18.skynet.dto.CarDTO;
import com.tim18.skynet.dto.CarReservationDTO;
import com.tim18.skynet.model.Branch;
import com.tim18.skynet.model.Car;
import com.tim18.skynet.model.CarReservation;
import com.tim18.skynet.model.RACAdmin;
import com.tim18.skynet.model.RegisteredUser;
import com.tim18.skynet.service.impl.BranchServiceImpl;
import com.tim18.skynet.service.impl.CarReservationServiceImpl;
import com.tim18.skynet.service.impl.CarServiceImpl;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.UserServiceImpl;



@Controller
public class CarController {

	@Autowired
	private CarServiceImpl carService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private CarReservationServiceImpl carReservationService;
	
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
		carService.delete(branch_id);
		return null;
	}
	
	


		@GetMapping(value = "/vehicles",produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Collection<Car>> getAll() {

			Collection<Car> vehicles = carService.findAll();

			return new ResponseEntity<>(vehicles, HttpStatus.OK);
		}

		@PreAuthorize("hasRole('ROLE_RENTACAR_ADMIN')")
		@PostMapping(value = "/vehicles", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Car> create(@RequestBody Car vehicle) {

			if (vehicle.getRegistrationNumber().trim().equals("") || vehicle.getRegistrationNumber() == null) {
				return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (carService.findByRegNumber(vehicle.getRegistrationNumber()) != null) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}

			Car veh = null;

			try {
				veh = carService.save(vehicle);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (veh == null) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>(veh, HttpStatus.OK);
		}

		@PreAuthorize("hasRole('ROLE_RENTACAR_ADMIN')")
		@PutMapping(value = "/vehicles",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Car> update(@RequestBody Car vehicle) {

			Car veh = null;
			try {
				veh = carService.save(vehicle);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (veh == null) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>(veh, HttpStatus.OK);
		}

		@GetMapping(value = "/vehicles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Car> findOne(@PathVariable("id") Long id) {
			Car veh = carService.findOne(id);

			return new ResponseEntity<>(veh, HttpStatus.OK);
		}
		/*
		@PreAuthorize("hasRole('ROLE_RENTACAR_ADMIN')")
		@DeleteMapping(value = "/vehicles/{id}")
		public ResponseEntity<Void> deleteVehicle(@PathVariable("id") Long id) {

			Car vehicle = carService.findOne(id);
			if (vehicle != null) {
				if (vehicle.getVehicleReservations().size() > 0) {
					return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
				}
				carService.delete(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

		@GetMapping(value = "/vehicles/{id}/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Collection<CarReservation>> getMyReservations(@PathVariable("id") Long id) {
			Car vehicle = carService.findOne(id);

			return new ResponseEntity<>(vehicle.getVehicleReservations(),
					HttpStatus.OK);
		}
		
		@PostMapping(value = "/vehicles/{id}/reservations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<CarReservation> addReservation(@PathVariable("id") Long id,
				@RequestBody CarReservationDTO vehResdto) {
			
			CarReservation vehRes = new CarReservation();
			vehRes.setCheckInDate(vehResdto.getCheckInDate());
			vehRes.setCheckOutDate(vehRes.getCheckOutDate());
			vehRes.setTotalPrice(vehResdto.getTotalPrice());
			
			CarReservation vehicleReservation = null;
			Car veh = null;
			RegisteredUser user = null;
			try {
				veh = carService.findOne(id);
				vehRes.setVehicle(veh);
				user = (RegisteredUser) userService.findOne(vehResdto.getUser_id());
				vehRes.setUser(user);
				vehicleReservation = carReservationService.save(vehRes);
				veh.getVehicleReservations().add(vehicleReservation);
				user.getVehicleReservations().add(vehicleReservation);
				carService.save(veh);
				userService.save(user);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (vehicleReservation == null) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>(vehicleReservation, HttpStatus.OK);
		}
		*/
	}
	
	
