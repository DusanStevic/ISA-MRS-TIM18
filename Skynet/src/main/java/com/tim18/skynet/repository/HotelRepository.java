package com.tim18.skynet.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tim18.skynet.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
	@Query("SELECT DISTINCT h FROM Hotel h " +
			   "WHERE lower(h.name) like lower(concat('%', ?1,'%')) " + 
			   "AND lower(h.address) like lower(concat('%', ?2,'%')) " +
			   "AND (h.id in "+
				   "(SELECT DISTINCT r.hotel.id FROM Room r "+
			   		"WHERE r.bedNumber <= ?5 "+
			   		"AND r.id NOT IN "+
			   			"(SELECT DISTINCT rr.reservedRoom.id FROM RoomReservation rr "+
			   			"WHERE ((rr.checkIn <= ?4 AND rr.checkOu >= ?4) " +
			   			"OR (rr.checkIn <= ?3 AND rr.checkOu >= ?4) " +
			   			"OR (rr.checkOu >= ?3 AND rr.checkOu <= ?4) " +
			   			"OR (rr.checkIn >= ?3 AND rr.checkOu <= ?4))))"+
			   	"OR h.id in " +
			   		"(SELECT DISTINCT r2.hotel.id FROM Room r2 "+
			   		"WHERE r2.id not in (SELECT rr2.reservedRoom.id FROM RoomReservation rr2) AND r2.bedNumber <= ?5))")
		ArrayList<Hotel> findByNameAndAddressAndDateAndBeds(String name, String address, Date checkin, Date checkout, int beds);
	
		@Query("SELECT DISTINCT h FROM Hotel h " +
			   "WHERE lower(h.address) like lower(concat('%', ?1,'%')) " + 
			   "AND (h.id in "+
			   		"(SELECT DISTINCT r.hotel.id FROM Room r "+
			   		"WHERE r.bedNumber <= ?4 "+
			   		"AND r.id NOT IN "+
			   			"(SELECT DISTINCT rr.reservedRoom.id FROM RoomReservation rr "+
			   			"WHERE ((rr.checkIn <= ?3 AND rr.checkOu >= ?3) " +
			   			"OR (rr.checkIn <= ?2 AND rr.checkOu >= ?3) " +
			   			"OR (rr.checkOu >= ?2 AND rr.checkOu <= ?3) " +
			   			"OR (rr.checkIn >= ?2 AND rr.checkOu <= ?3))))"+
			   "OR h.id in " +
			   		"(SELECT DISTINCT r2.hotel.id FROM Room r2 "+
			   		"WHERE r2.id not in (SELECT rr2.reservedRoom.id FROM RoomReservation rr2) AND r2.bedNumber <= ?4))")
		ArrayList<Hotel> findByAddressAndDateAndBeds(String address, Date checkin, Date checkout, int beds);

}
