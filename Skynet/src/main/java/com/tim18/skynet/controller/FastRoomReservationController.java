package com.tim18.skynet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.FastRoomReservationDTO;
import com.tim18.skynet.model.FastRoomReservation;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.HotelOffer;
import com.tim18.skynet.model.RegisteredUser;
import com.tim18.skynet.model.Reservation;
import com.tim18.skynet.model.Room;
import com.tim18.skynet.service.FastRoomReservationService;
import com.tim18.skynet.service.HotelOfferService;
import com.tim18.skynet.service.RoomService;
import com.tim18.skynet.service.impl.CustomUserDetailsService;

@RestController
public class FastRoomReservationController {
	@Autowired
	private FastRoomReservationService fastRoomReservationService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	
	@Autowired
	private HotelOfferService hotelOfferService;
	
	@Autowired
	private RoomService roomService;
	
	@RequestMapping( value="/api/fastRoomReservation",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public FastRoomReservation addFastReservation(@RequestBody FastRoomReservationDTO temp){
		List<HotelOffer> offers = new ArrayList<HotelOffer>();
		
		for(long id : temp.getHotelOffers()){
			HotelOffer offer = hotelOfferService.findOne(id);
			offers.add(offer);
		}
		
		Room room = roomService.findOne(temp.getRoomID());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		Date d2 = null;
		
		try {
			d1 = sdf.parse(temp.getStart());
			d2 = sdf.parse(temp.getEnd());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		Interval interval1 = new Interval(d1.getTime(), d2.getTime());
		for(FastRoomReservation fr2 : room.getFastReservations()){
			Date start = fr2.getStartDate();
			Date end = fr2.getEndDate();
			Interval interval2 = new Interval(start.getTime(), end.getTime());
			Interval overlap = interval2.overlap(interval1);
			if(overlap != null){
				return null;
			}
		}
		
		FastRoomReservation fr = new FastRoomReservation();
		fr.setDiscount(temp.getDiscount());
		fr.setOffers(offers);
		fr.setRoom(room);
		fr.setStartDate(d1);
		fr.setEndDate(d2);
		
		return fastRoomReservationService.save(fr);
	}
	
	@RequestMapping( value="/api/getfastRooms",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Room> getFastRooms(){
		HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return roomService.getFastHotelRooms(user.getHotel().getId());
	}
	
	@RequestMapping( value="/api/getfastRoomReservations/{room_id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FastRoomReservation> getFastReservations(@PathVariable(value = "room_id") Long room_id){
		//HotelAdmin user = (HotelAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Room room = roomService.findOne(room_id);
		return room.getFastReservations();
	}
}
