package com.tim18.skynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim18.skynet.model.Car;



public interface CarRepository extends JpaRepository<Car, Long>{

}

