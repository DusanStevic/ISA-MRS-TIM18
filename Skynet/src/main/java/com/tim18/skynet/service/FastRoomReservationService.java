package com.tim18.skynet.service;

import java.util.List;

import com.tim18.skynet.model.FastRoomReservation;

public interface FastRoomReservationService {
	public FastRoomReservation save(FastRoomReservation fastRoomReservation);
	public FastRoomReservation findOne(Long id);
	public List<FastRoomReservation> findAll();
	public void delete(Long id);
}
