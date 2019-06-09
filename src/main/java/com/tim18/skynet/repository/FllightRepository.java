package com.tim18.skynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim18.skynet.model.Flight;



public interface FllightRepository extends JpaRepository<Flight, Long> {
	

}
