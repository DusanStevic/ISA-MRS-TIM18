package com.tim18.skynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim18.skynet.model.Airline;

public interface AirlinRepository extends JpaRepository<Airline, Long>{

}
