package com.tim18.skynet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.CompanyDTO;
import com.tim18.skynet.dto.HotelSearchDTO;
import com.tim18.skynet.dto.ImageDTO;
import com.tim18.skynet.dto.RoomSearchDTO;
import com.tim18.skynet.model.Airline;
import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.Room;
import com.tim18.skynet.repository.HotelRepository;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.HotelServiceImpl;
import com.tim18.skynet.service.impl.RoomServiceImpl;

@RestController
public class HotelController {

	@Autowired
	private HotelServiceImpl hotelService;
	

	@Autowired
	private RoomServiceImpl roomService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	
	@GetMapping(value = "/api/getHotel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel() {
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Hotel hotel = user.getHotel();
		if (hotel != null) {
			return new ResponseEntity<>(hotel, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/api/editHotel", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> editHotel(@RequestBody CompanyDTO dto) {
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Hotel hotel = user.getHotel();
		if(dto.getAdress().equals("") == false){
			hotel.setAddress(dto.getAdress());
		}
		if(dto.getDescription().equals("") == false){
			hotel.setDescription(dto.getDescription());
		}
		if(dto.getName().equals("") == false){
			hotel.setName(dto.getName());
		}
		user.setHotel(hotel);
		return new ResponseEntity<>(hotelService.save(hotel), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/editHotelImage", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> editHotelImage(@RequestBody ImageDTO image) {
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Hotel hotel = user.getHotel();
		System.out.println(image.getUrl());
		hotel.setImage(image.getUrl());
		user.setHotel(hotel);
		return new ResponseEntity<>(hotelService.save(hotel), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/hotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> getAll() {
		return hotelService.findAll();
	}
	
	@RequestMapping(value = "/api/searchedHotels", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Hotel> getSearched(@RequestBody HotelSearchDTO search) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf.parse(search.getCheckin());
			date2 = sdf.parse(search.getCheckout());
		} catch (ParseException e) {
			System.out.println("Neuspesno parsiranje datuma");
			return null;
		}
		if(date1.before(new Date()) || date2.before(new Date())){
			return null;
		}
		
		int beds = search.getBeds();
		String name = search.getName();
		String address = search.getAddress();
		
		if(name == "" || name == null){
			name = null;
		}
		
		List<Hotel> hotels = hotelService.search(name, address, date1, date2, beds);
		return hotels;
	}
	
	@RequestMapping(value = "/api/hotel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hotel createHotel(@RequestBody Hotel hotel) {
		return hotelService.save(hotel);
	}
	
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);

		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.ok().body(hotel);
	}
	
	
	
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> deleteHotel(
			@PathVariable(value = "id") Long hotelId) {
			Hotel hotel = hotelService.findOne(hotelId);

			if (hotel != null) {
				hotelService.remove(hotelId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	
	
	@GetMapping(value = "/gradeHotel/{id}/{grade}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> createGrade(@PathVariable Long id, @PathVariable Integer grade) {
		Hotel hotel = hotelService.findOne(id);
		hotel.setScore(hotel.getScore() + grade);
		hotel.setNumber(hotel.getNumber() + 1);
		hotelService.save(hotel);
		return new ResponseEntity<>(hotel, HttpStatus.CREATED);
	}
}