package com.tim18.skynet.service;

import java.util.List;

import com.tim18.skynet.model.Car;

public interface CarService {
	public Car save(Car car);
	public Car findOne(Long id);
	public List<Car> findAll();
	public void remove(Long id);
}
