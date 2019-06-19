package com.tim18.skynet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.Interval;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tim18.skynet.dto.CarReservationDTO;
import com.tim18.skynet.dto.MessageDTO;
import com.tim18.skynet.dto.RoomReservationDTO;
import com.tim18.skynet.model.Car;
import com.tim18.skynet.model.CarReservation;
import com.tim18.skynet.model.HotelOffer;
import com.tim18.skynet.model.RegisteredUser;
import com.tim18.skynet.model.RentACar;
import com.tim18.skynet.model.Reservation;
import com.tim18.skynet.model.Room;
import com.tim18.skynet.model.RoomReservation;
import com.tim18.skynet.service.CarReservationService;
import com.tim18.skynet.service.CarService;
import com.tim18.skynet.service.RentACarService;
import com.tim18.skynet.service.ReservationService;
import com.tim18.skynet.service.impl.CustomUserDetailsService;




@Controller
public class CarReservationController {
	@Autowired
	CarReservationService carReservationService;

	@Autowired
	CarService carService;

	@Autowired
	RentACarService rentacarService;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	
	@Autowired
	private ReservationService reservationService;

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
	
	@RequestMapping( value="/api/getCarReservations/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CarReservation> getRoomReservations(@PathVariable(value = "id") Long id){
		RegisteredUser user = (RegisteredUser) this.userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			return null;
		}
		Reservation r = reservationService.findOne(id);
		return r.getCarReservations();
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

	@RequestMapping( value="/api/carReservation/{resid}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Reservation reserveCar(@RequestBody CarReservationDTO temp, @PathVariable(value = "resid") Long resID){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		RegisteredUser user = (RegisteredUser) this.userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if(user == null){
			return null;
		}
		
		Reservation reservation = reservationService.findOne(resID);
		
		Car car = carService.findOne(temp.getCarId());
		
		CarReservation carReservation = new CarReservation();
		
		//if(reservation.getPassangers().isEmpty() || reservation.getSeatReservations().isEmpty()){
			//return null;
		//}
		Date startDate = null;
		Date endDate = null;
		
		startDate = temp.getStartDate();
		endDate = temp.getEndDate();
		
		
		Interval interval1 = new Interval(startDate.getTime(), endDate.getTime());
		
		for(CarReservation reserv : car.getReservations()){
			Interval interval2 = new Interval(reserv.getStartDate().getTime(), reserv.getEndDate().getTime());
			Interval overlap = interval2.overlap(interval1);
			if(overlap != null){
				return null;
			}
		}
		
		double price = car.getPrice();
	
		carReservation.setReservation(reservation);
		carReservation.setCar(car);
		carReservation.setStartDate(startDate);
		carReservation.setEndDate(endDate);
		carReservation.setPrice(price);
		
		
		
		
		car.getReservations().add(carReservation);
		reservation.getCarReservations().add(carReservation);
		
		carReservationService.save(carReservation);
		carService.save(car);
		return reservationService.save(reservation);
	}
	
	@RequestMapping( value="/api/removeCarReservation/{id}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Reservation removeCarReservation(@PathVariable(value = "id") Long id){
		RegisteredUser user = (RegisteredUser) this.userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user == null){
			return null;
		}
		CarReservation rr = carReservationService.getOne(id);
		
		boolean isUser = false;
		for(RegisteredUser u : rr.getReservation().getPassangers()){
			if(u.getId() == user.getId()){
				isUser = true;
			}
		}
		if(isUser == false){
			return null;
		}
		Reservation r = rr.getReservation();
		rr.setReservation(null);
		rr.setCar(null);
		carReservationService.delete(id);
		return r;
	}
	
}

