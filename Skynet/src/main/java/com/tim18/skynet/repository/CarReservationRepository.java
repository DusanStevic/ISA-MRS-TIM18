package com.tim18.skynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim18.skynet.model.CarReservation;



public interface CarReservationRepository extends JpaRepository<CarReservation, Long>{

}
