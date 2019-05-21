package com.tim18.skynet.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tim18.skynet.model.Car;



public interface CarRepository extends JpaRepository<Car, Long>{
	
	Car findByRegistrationNumber(String registrationNumber);

	ArrayList<Car> findByTypeContainingIgnoreCaseAndGearContainingIgnoreCase(String type, String gear);
	
	@Query("SELECT v FROM Car v WHERE v.branch.rac.id = ?1 AND ?3 >= v.pricePerDay AND ?2 <= v.pricePerDay")
	ArrayList<Car> findByPriceRange(Long rentacarId, double minPrice, double maxPrice);
	
	@Query("SELECT DISTINCT v FROM Car v " +
		    "WHERE v.branch.rac.id = ?1 AND v.id NOT IN " +
					"(SELECT v2.id FROM Car v2, CarReservation vr " +
		             "WHERE v2.id = vr.vehicle.id " +
					 "AND ((vr.checkInDate <= ?2 AND vr.checkOutDate >= ?2) " +
		             "OR  (vr.checkInDate < ?3 AND vr.checkOutDate >= ?3) " +
					 "OR  (?2 <= vr.checkInDate AND ?3 >= vr.checkInDate)))")
	ArrayList<Car> findByAvailability(Long rentacarId, Date checkIn, Date checkOut);
}

