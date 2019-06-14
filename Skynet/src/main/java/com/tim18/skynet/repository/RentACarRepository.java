package com.tim18.skynet.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.RentACar;



public interface RentACarRepository extends JpaRepository<RentACar, Long> {
	
	@Query("SELECT r FROM RentACar r " +
			   "WHERE lower(r.name) like lower(concat('%', ?1,'%')) " + 
			   "AND lower(r.address) like lower(concat('%', ?2,'%'))")
		ArrayList<RentACar> findByNameAndAddress(String name, String address);
	
	
	@Query("SELECT r FROM RentACar r " +
			 "WHERE lower(r.name) like lower(concat('%', ?1,'%')) ")
	ArrayList<RentACar> findByName(String name);
	
	@Query("SELECT r FROM RentACar r " +
			   "WHERE lower(r.address) like lower(concat('%', ?2,'%'))")
	ArrayList<RentACar> findByAddress(String address);
	
	@Query("SELECT DISTINCT r FROM RentACar r " +
			   "WHERE lower(r.name) like lower(concat('%', ?1,'%')) " + 
			   "AND r.id in" +
			   		"(SELECT DISTINCT b.rac.id FROM Branch b " +
			   		"WHERE lower(b.address) like lower(concat('%', ?2,'%'))) " +
			   "AND (r.id in "+
				   "(SELECT DISTINCT v.branch.rac.id FROM Car v "+
			   		"WHERE v.id NOT IN "+
			   			"(SELECT DISTINCT vr.vehicle.id FROM CarReservation vr "+
			   			"WHERE ((vr.checkInDate <= ?4 AND vr.checkOutDate >= ?4) " +
			   			"OR (vr.checkInDate <= ?3 AND vr.checkOutDate >= ?4) " +
			   			"OR (vr.checkOutDate >= ?3 AND vr.checkOutDate <= ?4) " +
			   			"OR (vr.checkInDate >= ?3 AND vr.checkOutDate <= ?4))))"+
			   	"OR r.id in " +
			   		"(SELECT DISTINCT r2.branch.rac.id FROM Car r2 "+
			   		"WHERE r2.id not in (SELECT vr2.vehicle.id FROM CarReservation vr2)))")
		ArrayList<RentACar> findByNameAndAddressAndDateAndBeds(String name, String address, Date checkin, Date checkout);
	
		@Query("SELECT DISTINCT r FROM RentACar r " +
			   "WHERE r.id in" +
			   		"(SELECT DISTINCT b.rac.id FROM Branch b " +
			   		"WHERE lower(b.address) like lower(concat('%', ?1,'%'))) " +
			   "AND (r.id in "+
				   "(SELECT DISTINCT v.branch.rac.id FROM Car v "+
			   		"WHERE v.id NOT IN "+
			   			"(SELECT DISTINCT vr.vehicle.id FROM CarReservation vr "+
			   			"WHERE ((vr.checkInDate <= ?3 AND vr.checkOutDate >= ?3) " +
			   			"OR (vr.checkInDate <= ?2 AND vr.checkOutDate >= ?3) " +
			   			"OR (vr.checkOutDate >= ?2 AND vr.checkOutDate <= ?3) " +
			   			"OR (vr.checkInDate >= ?2 AND vr.checkOutDate <= ?3))))"+
			   	"OR r.id in " +
			   		"(SELECT DISTINCT r2.branch.rac.id FROM Car r2 "+
			   		"WHERE r2.id not in (SELECT vr2.vehicle.id FROM CarReservation vr2)))")
		ArrayList<RentACar> findByAddressAndDateAndBeds(String address, Date checkin, Date checkout);
			
			
	
	
	
}
