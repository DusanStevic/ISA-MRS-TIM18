package com.tim18.skynet.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.ReservationDTO;
import com.tim18.skynet.dto.RoomReservationDTO;
import com.tim18.skynet.model.HotelOffer;
import com.tim18.skynet.model.RegisteredUser;
import com.tim18.skynet.model.Reservation;
import com.tim18.skynet.model.Room;
import com.tim18.skynet.model.RoomReservation;
import com.tim18.skynet.model.SeatReservation;
import com.tim18.skynet.service.HotelOfferService;
import com.tim18.skynet.service.ReservationService;
import com.tim18.skynet.service.RoomReservationService;
import com.tim18.skynet.service.RoomService;
import com.tim18.skynet.service.impl.CustomUserDetailsService;


@RestController
public class RoomReservationController {
	
	@Autowired
	private RoomReservationService roomReservationService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private HotelOfferService hotelOfferService;
	
	@RequestMapping( value="/api/roomReservation",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Reservation reserveRoom(@RequestBody RoomReservationDTO temp){
		//RegisteredUser user = (RegisteredUser) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		//if(user == null){
			//return null;
		//}
		
		Reservation reservation = reservationService.findOne(temp.getReservationId());
		
		Room room = roomService.findOne(temp.getRoomId());
		System.out.println("Nasao sam sobu: "+room.getPrice());
		
		RoomReservation roomReservation = new RoomReservation();
		
		if(reservation.getPassangers().isEmpty() || reservation.getSeatReservations().isEmpty()){
			return null;
		}
		
		Date startDate = reservation.getSeatReservations().get(0).getCheckOu();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, temp.getDays());
		Date endDate = calendar.getTime();
		
		double price = room.getPrice();
		for(long id : temp.getHotelOffers()){
			HotelOffer hotelOffer = hotelOfferService.findOne(id);
			if(hotelOffer != null){
				price += hotelOffer.getPrice();
				hotelOffer.getRoomReservations().add(roomReservation);
				roomReservation.getHotelOffers().add(hotelOffer);
			}
		}
		roomReservation.setReservation(reservation);
		roomReservation.setCheckIn(startDate);
		roomReservation.setCheckOu(endDate);
		roomReservation.setReservedRoom(room);
		roomReservation.setPrice(price);
		
		room.getReservations().add(roomReservation);
		reservation.getRoomReservations().add(roomReservation);
		
		roomReservationService.save(roomReservation);
		//reservationService.save(reservation);
		roomService.save(room);
		
		return reservation;
	}
	
	@RequestMapping( value="/api/getAvailableRooms/res_id",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Room> getAvailableRooms(){
		
		return null;
	}
	
	@RequestMapping( value="/api/getReservation",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReservationDTO getReservation(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd. MM. yyyy.");
		Date date = new Date();
		String date1 = sdf.format(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 10);
		String date2 = sdf.format(calendar.getTime());
		return new ReservationDTO(date1, date2, 10);
	}
}
