package com.tim18.skynet.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.comparator.RoomPriceComparator;
import com.tim18.skynet.comparator.RoomPriceComparatorHighToLow;
import com.tim18.skynet.dto.ImageDTO;
import com.tim18.skynet.dto.RoomDTO;
import com.tim18.skynet.dto.RoomOffersDTO;
import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.Room;
import com.tim18.skynet.model.RoomOffer;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.HotelServiceImpl;
import com.tim18.skynet.service.impl.RoomServiceImpl;

@RestController
public class RoomController {
	@Autowired
	private RoomServiceImpl roomService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	
	@Autowired
	private HotelServiceImpl hotelService;
	

	@RequestMapping( value="/api/room",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Room createRoom(@Valid @RequestBody Room room) {
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		room.setHotel(user.getHotel());
		return roomService.save(room);
		
	}
	
	@RequestMapping( value="/api/getRooms",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Room> getRooms(@Valid @RequestBody RoomOffersDTO sort) {
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		System.out.println(sort.getSort());
		if(sort.getSort().equals("1")){
			return sortRoomsLowToHigh(user.getHotel().getRooms());
		}
		else{
			return sortRoomsHighToLow(user.getHotel().getRooms());
		}
	}
	
	@RequestMapping( value="/api/searchForRooms",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Room> searchRooms( @Valid @RequestBody RoomOffersDTO roomOffers) {
		System.out.println(roomOffers.getSort());
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(roomOffers.getRoomOffers().isEmpty()){
			return user.getHotel().getRooms();
		}
		List<Room> rooms = new ArrayList<Room>();
		for(Room r : user.getHotel().getRooms()){
			boolean containsAll = true;
			if(r.getRoomOffers().size() > 0){
				for(String off : roomOffers.getRoomOffers()){
					if(r.containsOffer(off) == false){
						containsAll = false;
						break;
					}
				}
				if(containsAll){
					rooms.add(r);
				}
			}
		}
		if(roomOffers.getSort().equals("1")){
			return sortRoomsLowToHigh(rooms);
		}
		else{
			return sortRoomsHighToLow(rooms);
		}
	}
	
	@RequestMapping( value="/api/searchRooms/{id}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Room> searchRoomsID(@PathVariable(value = "id") Long id, @Valid @RequestBody RoomOffersDTO roomOffers) {
		Hotel hotel = hotelService.findOne(id);
		if(roomOffers.getRoomOffers().isEmpty()){
			return hotel.getRooms();
		}
		List<Room> rooms = new ArrayList<Room>();
		for(Room r : hotel.getRooms()){
			boolean containsAll = true;
			if(r.getRoomOffers().size() > 0){
				for(String off : roomOffers.getRoomOffers()){
					if(r.containsOffer(off) == false){
						containsAll = false;
						break;
					}
				}
				if(containsAll){
					rooms.add(r);
				}
			}
		}
		if(roomOffers.getSort().equals("1")){
			return sortRoomsLowToHigh(rooms);
		}
		else{
			return sortRoomsHighToLow(rooms);
		}
	}
	
	@RequestMapping( value="/api/getRooms/{hotel_id}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Room> getHotelRooms(@PathVariable(value = "hotel_id") Long hotel_id, @Valid @RequestBody RoomOffersDTO roomOffers) {
		Hotel hotel = hotelService.findOne(hotel_id);
		if(roomOffers.getSort().equals("1")){
			return sortRoomsLowToHigh(hotel.getRooms());
		}
		else{
			return sortRoomsHighToLow(hotel.getRooms());
		}
	}
	
	@RequestMapping( value="/api/getRoom/{room_id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Room getRoom(@PathVariable(value = "room_id") Long room_id) {
		Room room = roomService.findOne(room_id);
		return room;
	}
	
	@RequestMapping( value="/api/editRoom",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Room editRoom(@RequestBody RoomDTO room) {
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		for(Room r : user.getHotel().getRooms()){
			if(r.getId() == room.getId()){
				if(room.getBeds() != r.getBedNumber() && room.getBeds() > 0){
					r.setBedNumber(room.getBeds());
				}
				if(room.getPrice() != r.getPrice() && room.getPrice() > 0){
					r.setPrice(room.getPrice());
				}
				if(room.getDescription().equals(r.getDescription()) == false && room.getDescription().equals("") == false){
					r.setDescription(room.getDescription());
				}
				return roomService.save(r);
			}
		}
		return null;
	}
	
	public List<Room> sortRoomsLowToHigh(List<Room> rooms){
		RoomPriceComparator comparator = new RoomPriceComparator();
		Collections.sort(rooms, comparator);
		return rooms;
	}
	
	public List<Room> sortRoomsHighToLow(List<Room> rooms){
		RoomPriceComparatorHighToLow comparator = new RoomPriceComparatorHighToLow();
		Collections.sort(rooms, comparator);
		return rooms;
	}
	
	@RequestMapping(value = "/api/addRoomImage/{room_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Room> editHotelImage(@RequestBody ImageDTO image, @PathVariable(value = "room_id") Long room_id) {
		Room room = roomService.findOne(room_id);
		room.setImage(image.getUrl());
		return new ResponseEntity<>(roomService.save(room), HttpStatus.OK);
	}
	
	@RequestMapping( value="/api/deleteRoom/{room_id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Room deleteRoom(@PathVariable(value = "room_id") Long room_id) {
		Room room = roomService.findOne(room_id);
		room.setHotel(null);
		roomService.delete(room_id);
		return null;
	}
}