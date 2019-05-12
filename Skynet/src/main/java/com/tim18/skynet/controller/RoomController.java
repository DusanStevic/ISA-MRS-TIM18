package com.tim18.skynet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.RoomDTO;
import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.Room;
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
	
	@RequestMapping( value="/api/getRooms",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Room> getRooms() {
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return user.getHotel().getRooms();
	}
	
	@RequestMapping( value="/api/getRooms/{hotel_id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Room> getHotelRooms(@PathVariable(value = "hotel_id") Long hotel_id) {
		Hotel hotel = hotelService.findOne(hotel_id);
		return hotel.getRooms();
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
					System.out.println("ovde sam "+ room.getBeds());
					r.setBedNumber(room.getBeds());
				}
				if(room.getPrice() != r.getPrice() && room.getPrice() > 0){
					r.setPrice(room.getPrice());
				}
				if(room.getImage().equals(r.getImage()) == false && room.getImage().equals("") == false){
					r.setImage(room.getImage());
				}
				if(room.getDescription().equals(r.getDescription()) == false && room.getDescription().equals("") == false){
					r.setDescription(room.getDescription());
				}
				return roomService.save(r);
			}
		}
		return null;
	}
	
	@RequestMapping( value="/api/deleteRoom/{room_id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Room deleteRoom(@PathVariable(value = "room_id") Long room_id) {
		Room room = roomService.findOne(room_id);
		room.setHotel(null);
		roomService.delete(room_id);
		return null;
	}
}