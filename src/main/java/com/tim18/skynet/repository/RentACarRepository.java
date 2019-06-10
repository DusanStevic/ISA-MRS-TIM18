package com.tim18.skynet.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
			
			
	
	
	
}
