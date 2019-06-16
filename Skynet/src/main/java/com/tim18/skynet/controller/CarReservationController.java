package com.tim18.skynet.controller;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.CarReservationDTO;
import com.tim18.skynet.dto.MessageDTO;
import com.tim18.skynet.model.Car;
import com.tim18.skynet.model.CarReservation;
import com.tim18.skynet.model.RegisteredUser;
import com.tim18.skynet.model.RentACar;
import com.tim18.skynet.service.CarReservationService;
import com.tim18.skynet.service.CarService;
import com.tim18.skynet.service.RentACarService;
import com.tim18.skynet.service.impl.CarReservationServiceImpl;
import com.tim18.skynet.service.impl.CustomUserDetailsService;


@RestController
public class CarReservationController {

	@Autowired
	CarReservationService carReservationService;

	@Autowired
	CarService carService;

	@Autowired
	RentACarService rentacarService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@SuppressWarnings("deprecation")
	@PutMapping(value = "/api/putCarOnFastRes/{carId}/{startDate}/{endDate}/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putCarOnFastRes(@PathVariable Long carId, @PathVariable String startDate,
			@PathVariable String endDate, @PathVariable Double price) {
		@SuppressWarnings("deprecation")
		Date startDatee = new Date(Integer.parseInt(startDate.split("\\-")[0]) - 1900,
				Integer.parseInt(startDate.split("\\-")[1]) - 1, Integer.parseInt(startDate.split("\\-")[2]));
		@SuppressWarnings("deprecation")
		Date endDatee = new Date(Integer.parseInt(endDate.split("\\-")[0]) - 1900,
				Integer.parseInt(endDate.split("\\-")[1]) - 1, Integer.parseInt(endDate.split("\\-")[2]));

		Car c = carService.findOne(carId);
		Boolean dozvola = true;
		for (CarReservation res : c.getReservations()) {
			if (res.getStartDate().compareTo(startDatee) < 0 && res.getEndDate().compareTo(startDatee) < 0) {
				dozvola = false;
			}
			if (res.getStartDate().compareTo(endDatee) < 0 && res.getEndDate().compareTo(endDatee) > 0) {
				dozvola = false;
			}
			if (res.getStartDate().compareTo(endDatee) < 0 && res.getEndDate().compareTo(endDatee) < 0) {
				dozvola = false;
			}
		}

		if (dozvola) {
			c.setOnFastRes(true);
			c.setFastResStartDate(startDatee);
			c.setFastResEndDate(endDatee);
			c.setFastResPrice(price);
			carService.save(c);
			return new ResponseEntity<>(c, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new MessageDTO("Car is reserved, it cannot be put on fast res.", "Error"),
					HttpStatus.OK);
		}

	}

	@GetMapping(value = "/findRentacarFromRes/{resId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> findRentacarFromRes(@PathVariable Long resId) {
		CarReservation res = carReservationService.getOne(resId);
		RentACar rentacar = res.getRentacarRes();
		return new ResponseEntity<>(rentacar, HttpStatus.OK);
	}

	@GetMapping(value = "/findCarFromRes/{resId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> findCarFromRes(@PathVariable Long resId) {
		CarReservation res = carReservationService.getOne(resId);
		Car car = res.getCar();
		return new ResponseEntity<>(car, HttpStatus.OK);
	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/createCarReservation/{carId}/{startDate}/{endDate}/{passengers}/{flight_res}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CarReservation> create(@PathVariable Long carId, @PathVariable String startDate,
			@PathVariable String endDate, @PathVariable Integer passengers, @PathVariable String flight_res) {
		RegisteredUser user = (RegisteredUser) this.userDetailsService
				.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		/*Integer flights = 0;
		if (user.getFlightReservations() != null && !user.getFlightReservations().isEmpty()) {
			flights = user.getFlightReservations().size();

		}*/
		Integer cars = 0;
		if (user.getCarReservations() != null && !user.getCarReservations().isEmpty()) {
			cars = user.getCarReservations().size();
		}/*
		Integer hotels = 0;
		if (user.getRoomReservations() != null && !user.getRoomReservations().isEmpty()) {
			hotels = user.getRoomReservations().size();
		}*/

		Integer discount;
		Integer numberOfRes = /*flights +*/ cars /*+ hotels*/;
		if (numberOfRes >= 3 && numberOfRes < 10) {
			discount = 5;
		} else if (numberOfRes >= 10 && numberOfRes < 30) {
			discount = 10;
		} else if (numberOfRes >= 30 && numberOfRes < 100) {
			discount = 20;
		} else if (numberOfRes >= 100) {
			discount = 40;
		} else {
			discount = 0;
		}

		@SuppressWarnings("deprecation")
		Date startDatee = new Date(Integer.parseInt(startDate.split("\\-")[0]) - 1900,
				Integer.parseInt(startDate.split("\\-")[1]) - 1, Integer.parseInt(startDate.split("\\-")[2]));
		@SuppressWarnings("deprecation")
		Date endDatee = new Date(Integer.parseInt(endDate.split("\\-")[0]) - 1900,
				Integer.parseInt(endDate.split("\\-")[1]) - 1, Integer.parseInt(endDate.split("\\-")[2]));

		/*
		List<FlightReservation> flightRes = user.getFlightReservations();

		FlightReservation lastRes = null;
		if (flight_res.equals("1")) {
			lastRes = flightRes.get(0);
			for (FlightReservation fr : flightRes) {
				if (fr.getId() > lastRes.getId()) {
					lastRes = fr;
				}
			}
		}*/

		CarReservationDTO dto = new CarReservationDTO(carId, startDatee, endDatee, passengers);
		CarReservation newCarRes = new CarReservation(dto);
		Car car = carService.findOne(dto.getCarId());
		/*if (flight_res.equals("1")) {
			newCarRes.setFlightId(lastRes.getId());
		} else {
			newCarRes.setFlightId(Long.parseLong("-1"));
		}*/

		newCarRes.setCar(car);
		if (discount == 0) {
			newCarRes.setPrice(car.getPrice());
		} else {
			newCarRes.setPrice(car.getPrice() * (100 - discount) / 100);
		}

		newCarRes.setRegularUser(user);
		newCarRes.setNumOfPass(passengers);
		newCarRes.setDiscount(discount);

		RentACar rentacar = rentacarService.findOne(car.getRentacar().getId());
		newCarRes.setRentacarRes(rentacar);

		carReservationService.save(newCarRes);

		user.getCarReservations().add(newCarRes);
		rentacar.getCarReservations().add(newCarRes);
		car.getReservations().add(newCarRes);

		return new ResponseEntity<>(newCarRes, HttpStatus.CREATED);
	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/createCarReservationFast/{carId}/{startDate}/{endDate}/{flightRes}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CarReservation> createCarReservationFast(@PathVariable Long carId,
			@PathVariable String startDate, @PathVariable String endDate, @PathVariable String flightRes) {
		RegisteredUser user = (RegisteredUser) this.userDetailsService
				.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		@SuppressWarnings("deprecation")
		Date startDatee = new Date(Integer.parseInt(startDate.split("\\-")[0]) - 1900,
				Integer.parseInt(startDate.split("\\-")[1]) - 1, Integer.parseInt(startDate.split("\\-")[2]));

		@SuppressWarnings("deprecation")
		Date endDatee = new Date(Integer.parseInt(endDate.split("\\-")[0]) - 1900,
				Integer.parseInt(endDate.split("\\-")[1]) - 1, Integer.parseInt(endDate.split("\\-")[2]));

		//List<FlightReservation> flightRes2 = user.getFlightReservations();
/*
		FlightReservation lastRes = null;
		if (flightRes.equals("1")) {
			lastRes = flightRes2.get(0);
			for (FlightReservation fr : flightRes2) {
				if (fr.getId() > lastRes.getId()) {
					lastRes = fr;
				}
			}
		}*/

		CarReservation newCarRes = new CarReservation(startDatee, endDatee);
		Car car = carService.findOne(carId);
		/*if (flightRes.equals("1")) {
			newCarRes.setFlightId(lastRes.getId());
		} else {
			newCarRes.setFlightId(Long.parseLong("-1"));
		}*/

		newCarRes.setCar(car);
		newCarRes.setPrice(car.getFastResPrice());
		newCarRes.setRegularUser(user);
		RentACar rentacar = rentacarService.findOne(car.getRentacar().getId());
		newCarRes.setRentacarRes(rentacar);

		carReservationService.save(newCarRes);
		user.getCarReservations().add(newCarRes);
		rentacar.getCarReservations().add(newCarRes);
		car.getReservations().add(newCarRes);

		return new ResponseEntity<>(newCarRes, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/cancelCarReservation/{resId}")
	public ResponseEntity<?> cancelCarReservation(@PathVariable Long resId) {
		RegisteredUser user = (RegisteredUser) this.userDetailsService
				.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		userDetailsService.saveUser(user);
		CarReservation carRes = carReservationService.getOne(resId);
		Car car = carRes.getCar();
		int brojac2 = -1;
		for (CarReservation cr : car.getReservations()) {
			brojac2++;
			if (cr.getId().equals(resId)) {
				car.getReservations().remove(brojac2);
			}
		}
		carService.save(car);
		carReservationService.delete(resId);

		return new ResponseEntity<>(carRes, HttpStatus.OK);

	}
}

