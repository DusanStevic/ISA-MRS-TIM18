package com.tim18.skynet.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tim18.skynet.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	@Query("SELECT r FROM Room r " +
			   "WHERE r.hotel.id = ?1 " + 
			   "AND r.bedNumber <= ?4 "+
			   "AND r.id NOT IN "+
			   		"(SELECT DISTINCT rr.reservedRoom.id FROM RoomReservation rr "+
			   		"WHERE ((rr.checkIn <= ?3 AND rr.checkOu >= ?3) " +
			   		"OR (rr.checkIn <= ?2 AND rr.checkOu >= ?3) " +
			   		"OR (rr.checkOu >= ?2 AND rr.checkOu <= ?3) " +
			   		"OR (rr.checkIn >= ?2 AND rr.checkOu <= ?3)))")
	ArrayList<Room> findAvailable(long hotelId, Date checkin, Date checkout, int beds);
}